#version 410 core

layout(location = 0) out vec4 fcolor;
layout(location = 1) out vec4 bright;

in vertexData{
    vec4 vertexColor;
    vec2 textureCoord;
    vec3 lightdir;
    vec3 eyedir;
    vec4 pos;
    vec3 norm;
    vec4 shadowpos;
    float visibility;
};


struct Material
{
    bool hasnormmap;
    bool hasspecmap;
    bool hasspecpow;
    bool hasambmap;
    vec3 ks;
    vec3 ka;
    vec3 kd;
    float ns;
};
struct Light
{
    float lightdistance;
    float lightpower;
    vec3 lightpos;
    vec3 color;
};

uniform float time;
uniform int text;
uniform mat4 shmvp;
uniform mat4 view;
uniform mat4 model;
uniform float uvmultx;
uniform float uvmulty;
uniform sampler2D Kd;
uniform sampler2D Ka;
uniform sampler2D Ks;
uniform sampler2D Ns;
uniform sampler2D bump;
uniform samplerCube cubemap;
uniform Material material;
uniform Light light;
uniform int mode;

float bias = 0.005;
float bloomMin = 0.9;
float vis = 1;

vec4 color;
vec3 diffuse;
float trans;
vec3 ambient;
vec3 specular;
float specpow;
vec3 n;

mat3 cotangent_frame( vec3 N, vec3 p, vec2 uv ){
    // get edge vectors of the pixel triangle
    vec3 dp1 = dFdx( p );
    vec3 dp2 = dFdy( p );
    vec2 duv1 = dFdx( uv );
    vec2 duv2 = dFdy( uv );
 
    // solve the linear system
    vec3 dp2perp = cross( dp2, N );
    vec3 dp1perp = cross( N, dp1 );
    vec3 T = dp2perp * duv1.x + dp1perp * duv2.x;
    vec3 B = dp2perp * duv1.y + dp1perp * duv2.y;
 
    // construct a scale-invariant frame 
    float invmax = inversesqrt( max( dot(T,T), dot(B,B) ) );
    return mat3( T * invmax, B * invmax, N );
}

vec3 calculatenormal( vec3 N, vec3 V, vec2 texcoord ){
    vec3 map = texture( bump, texcoord ).xyz;
    map = map * 255./127. - 128./127.;
    mat3 TBN = cotangent_frame( N, -V, texcoord );
    return normalize( TBN * map );
}


vec4 getTex(sampler2D tname){
    if(text == 1){
        vec4 col = texture(tname, textureCoord);
        float width = 0.4f;
        float edge = 0.2f;
        float dist = 1-col.a;
        float alpha = 1-smoothstep(width,width+edge,dist);
        vec3 colr = col.rgb;
        return vec4(colr, alpha);
    }
    return texture(tname, textureCoord);
}

vec4 shadify(){
	
    float distance = length( light.lightpos - pos.xyz );

    vec3 l = normalize( lightdir );   
    float cosTheta = clamp( dot( n,l ), 0,1 );
    
    
    vec3 E = normalize(eyedir);
    vec3 R = reflect(-l,n);
    float cosAlpha = clamp( dot( E,R ), 0,1 );
    
    float distmult = ((distance*distance)/light.lightdistance); 
    
    vec4 fragColor = 
            vec4(((diffuse * light.color * light.lightpower * cosTheta / distmult) +
            (specular * light.color * light.lightpower * pow(cosAlpha, specpow) / distmult)), trans);
 
    return fragColor;
}

void process(){
	color = getTex(Kd);

    diffuse = color.rgb;

    trans = color.a;
    
    if(trans < 0.2) discard;
    
    ambient = 0.2f * diffuse;
    
    specular = vec3(1,1,1);
    
    specpow = 1;
    if(material.hasspecpow){
        vec4 specpowvec = getTex(Ns);
        specpow = (specpowvec.r);
    }else{
        specpow = material.ns;
    }
	specpow = 5;
    if(material.hasspecmap){
        specular = getTex(Ks).rgb;
    }else{
        specular = material.ks;
    }
	
	n = normalize(( view * model *  vec4(norm,0.0f)).xyz);
    
    if(material.hasnormmap){
       n = calculatenormal(n,eyedir,textureCoord);
    }
}

void main() {   
	process();
    fcolor = shadify();
	
	float brightness = (fcolor.r + fcolor.g + fcolor.z) / 3.0;
	if(brightness > bloomMin){
		bright = fcolor;
	}else{
		bright = vec4(0,0,0,1);
	}
};


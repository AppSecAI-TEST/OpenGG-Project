#version 410 core
#define LIGHTNUM 100


layout(location = 0) out vec4 fcolor;

in vertexData{
    vec4 vertexColor;
    vec2 textureCoord;
    vec4 pos;
    vec3 norm;
};
struct Light
{ 
    vec3 lightpos;
    vec3 color;
	float lightdistance;
	float lightdistance2;
};

layout (std140) uniform LightBuffer {
	Light lights[LIGHTNUM];
};

uniform float uvmultx;
uniform int numLights;
uniform mat4 view;
uniform mat4 model;
uniform vec3 camera;
uniform sampler2D Kd;
uniform samplerCube cubemap;

float trans;
float specpow;
vec3 eyedir;
vec3 reflectedcolor;
vec3 n;
vec3 ambient;
vec3 specular;
vec3 diffuse;
vec4 color;
vec4 finalcolor;

void genPhong(){
    vec3 positionRelativeToCam = (view * model * vec4(pos.xyz, 1.0f)).xyz;
    
    vec3 posCameraspace = (positionRelativeToCam);
    eyedir = vec3(0,0,0) - posCameraspace;
}

vec3 shadify(Light light){

	float distance = length( light.lightpos - pos.xyz ); 
	float attenuation =  clamp((1.0 - distance/light.lightdistance), 0.0, 1.0);
	attenuation = attenuation * attenuation;

	vec3 lightDir = normalize(light.lightpos - pos.xyz);
	vec3 halfwayDir = normalize(lightDir + eyedir);

    float cosTheta = max(dot( n,lightDir ), 0.0f );
    vec3 fdif = diffuse * light.color * cosTheta * attenuation;
	
    float cosAlpha = clamp(max(dot(n, halfwayDir), 0.0), 0, 1);
	vec3 fspec = specular * light.color * pow(cosAlpha, specpow) * attenuation;
	
    vec3 fragColor = fdif + fspec;
	
	return fragColor;
}

vec4 getTex(sampler2D tname){
    return texture(tname, textureCoord * uvmultx);
}

void process(){
    diffuse = getTex(Kd).rgb;

    ambient = 0.2f * diffuse;
    
    specpow = 10;

    specular = vec3(1,1,1);
    
	n = normalize(( model * vec4(norm,0.0f)).xyz);

	reflectedcolor = texture(cubemap, 
		normalize(reflect(pos.xyz - camera, n))).xyz;
	
	ambient = ambient * reflectedcolor;
}

void main() {  
	genPhong();
	process();
	vec3 col = vec3(0,0,0);
	
	for(int i = 0; i < numLights; i++){
		col += shadify(lights[i]);
	}

	fcolor = vec4(col + ambient, 0.5f);
};
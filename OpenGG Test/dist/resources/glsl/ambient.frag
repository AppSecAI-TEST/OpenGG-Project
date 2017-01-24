#version 410 core

layout(location = 0) out vec4 fcolor;

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

uniform sampler2D Kd;
uniform sampler2D Ka;

vec2 camerarange = vec2(1280, 960);
vec2 screensize = vec2(1280, 960);

vec4 getTex(sampler2D tname){
    return texture(tname, textureCoord);
}

void main() { 
	vec4 color = getTex(Kd);  
    vec3 diffuse = color.rgb;

    float trans = color.a;
    
    if(trans < 0.2) discard;
    
    vec3 ambient = 0.1f * diffuse;
	
	fcolor = vec4(ambient.xyz, trans);
};

#version 330

smooth in vec4 theColor,
	   		   thePositionWorld,
	   		   newNormal,
	   		   thePosition;
smooth in vec3 theLightPos;

smooth in vec3 theAmbColor,
	   		   theDifColor,
	   		   theSpeColor;

smooth in float kAO,
	   			kDO,
	   			kSO,
	   			sNO;

smooth in mat4 theModelView;

out vec4 outputColor;

void main()
{
	//diffuse
    vec3 lightDir = normalize(theLightPos - thePositionWorld.xyz);
    float iD = max(0.0, dot(lightDir, newNormal.xyz));

    //specular
    vec3  v  = -normalize((theModelView * thePosition).xyz);
    vec3  h  =  normalize(lightDir + v);
    float iS =  pow(max(0.0, dot(newNormal.xyz, h)), sNO);

    vec3 lightFactor = kAO * theAmbColor + kDO * iD * theDifColor + kSO * iS * theSpeColor;

    outputColor = vec4(theColor.rgb * lightFactor, theColor.a);
}

#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec4 normal;
layout (location = 2) in vec4 color;

//matrix
uniform mat4 modelmatrix,
			 viewmatrix,
			 projectionmatrix;

//light parameters
uniform vec3 lightPos;

uniform vec3 ambientColor,
			 diffuseColor,
			 speclarColor;
uniform float kA, 
			  kD, 
			  kS, 
			  sN;

//vertex color
smooth out vec4 theColor,
	   			thePositionWorld,
	   			newNormal,
	   			thePosition;

//light parameters
smooth out vec3 theLightPos;

smooth out vec3 theAmbColor,
	   			theDifColor,
	   			theSpeColor;

smooth out float kAO,
	   			 kDO,
	   			 kSO,
	   			 sNO;

smooth out mat4 theModelView;

void main()
{
    mat4 modelView = viewmatrix * modelmatrix;
    theModelView = modelView;
    mat4 normalMatrix = transpose(inverse(modelView));

    // final vertex position
    gl_Position = projectionmatrix * modelView * position;

    thePositionWorld = modelmatrix * position;
    newNormal = normalize(normalMatrix * normal);

    theLightPos = lightPos;
    theAmbColor = ambientColor;
    theSpeColor = speclarColor;
    theDifColor = diffuseColor;

    kAO = kA;
    kDO = kD;
    kSO = kS;
	sNO = sN;

	theColor = color;
	thePosition = position;
}

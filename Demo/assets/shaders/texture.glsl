#type vertex
#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texCord;

uniform mat4 model;
uniform mat4 projection;

out vec2 v_texCord;


void main() {
    gl_Position = projection * model * vec4(position, 0, 1);
    v_texCord = texCord;
}


#type fragment
#version 330 core

uniform sampler2D tex;

in vec2 v_texCord;

out vec4 color;

void main() {
    color = texture(tex, v_texCord);
}
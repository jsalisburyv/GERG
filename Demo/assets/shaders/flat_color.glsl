#type vertex
#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texCoord;
uniform mat4 projection;

out vec4 col;

void main() {
    gl_Position = projection * vec4(position, 0.0, 1.0);
    col = color;
}

#type fragment
#version 330 core

in vec4 col;
out vec4 color;

void main() {
    color = col;
}

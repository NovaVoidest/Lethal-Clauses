#version 150

uniform sampler2D DiffuseSampler;
uniform float Intensity;
uniform vec3 FlashColor;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 center = vec2(0.5, 0.5);
    vec2 dir = texCoord - center;
    float dist = length(dir);

    float aberration = Intensity * 0.500 * dist;
    float r = texture(DiffuseSampler, texCoord - dir * aberration).r;
    float g = texture(DiffuseSampler, texCoord).g;
    float b = texture(DiffuseSampler, texCoord + dir * aberration).b;
    vec3 sceneColor = vec3(r, g, b);

    float radialGlow = smoothstep(1.2, 0.0, dist);
    vec3 flashOverlay = FlashColor * Intensity * (0.65 + 0.55 * radialGlow);

    vec3 result = sceneColor + flashOverlay + (sceneColor * flashOverlay * 0.8);

    fragColor = vec4(result, 1.0);
}
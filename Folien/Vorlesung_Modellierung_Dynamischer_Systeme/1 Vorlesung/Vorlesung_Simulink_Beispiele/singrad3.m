% Sinus f�r Winkel in Grad
function y = singrad3(x) 
    y = sin(GradInBogenmass(x));
return

% lokale Funktion
function bog = GradInBogenmass(grd)
    bog = grd*pi/180;
return
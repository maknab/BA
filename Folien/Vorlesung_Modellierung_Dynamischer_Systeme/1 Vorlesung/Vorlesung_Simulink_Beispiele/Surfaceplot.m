[x y] = meshgrid(-10 : 0.5 : 10); % erzeuge f�r jeden Gitterpunkt ein x-y-Paar
R     = sqrt(x.^2 + y.^2) + eps;  % Berechne Abstand eines Gitterpunktes von (0,0).
                                  % x.^2: quadriere jeden x-Wert des Vektors x
                                  % Addiere einen sehr, sehr kl. Wert eps 
                                  % --> damit wir R nie ganz 0.
Z     = sin(R)./R;                % Dividiere Z�hler und Nenner elementeweise
surf(x,y,Z);                      % Plotte
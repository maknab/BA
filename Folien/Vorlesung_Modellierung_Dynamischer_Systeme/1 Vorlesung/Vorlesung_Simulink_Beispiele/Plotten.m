x = -pi:0.01:pi;   % Vektor von -Pi bis + Pi in 0.01-er Schritten
y1 = sin(x);       % Vektor der Sinuswerte
plot(x,y1);        % Funktion plotten

hold on;           % weitere Funktion in die gleiche "figure" plotten 

y2 = cos(x);       % Vektor der Cosinuswerte
plot(x,y2, 'r--', 'linewidth', 3);  % Funktion plotten (rot gestrichelt)
                                    % Liniendicke = 3

title('Mein Testplot');   % Plot beschriften
grid on;                  % Raster einblenden


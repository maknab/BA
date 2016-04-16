x1 = [1  2  3];  % Zeilenvektor (Trennung durch Leerzeichen)
x2 = [4, 5, 6];  % Zeilenvektor (Trennung durch Kommata)
x3 = [1; 2; 3];  % Spaltenvektor (Trennung durch Semikolon)
x4 = [4  5  6]'; % Spaltenvektor durch Transponieren eines 
                 % Zeilenvektors
x5 = 4:6;        % Erzeugen und Initialisieren eines Vektors
                 % [4 5 6]
x6 = 1:2:5;      % Erzeugen und Initialisieren eines Vektors
                 % [1 3 5] (von 1 bis 5 in Zweierschritten)
                 
x7 = [1 2 3 4 5 6 7 8];    
x8  = zeros(1, 3); % erzeugt Zeilenvektor  [0 0 0]
x9  = zeros(3, 1); % erzeugt Spaltenvektor [0 0 0]'
x10 = ones(1, 3);  % erzeugt Zeilenvektor  [1 1 1]
x11 = rand(3,1);   % erzeugt Spaltenvektor (3 Zeile, 1 Spalte)
                   % mit Zufallszahlen

% Der Index beginnt bei Matlab bei 1 (nicht bei 0, wie in c/Java)                 
a = x7(2);       % Zugriff auf das 2. Element (a=2)
b = x7(2:4);     % Zugriff auf die Elemente 2 bis 4 
                 % d.h. :  b = [2 3 4]
c = 3*x1;        % Multiplikation : Skalar mit Vektor
                 % d.h. :  c = [3 6 9]
d = x1*x2';      % Skalarprodukt zweier Zeilenvektoren
                 % x2 muss zuvor in Spaltenvektor umgewandelt werden
                 % d = 32
e = x1*x3;       % Skalarprodukt Zeilenvektor mit Spaltenvektor
                 % e = 14
f = x1.*x2;      % Elementeweise Multiplikation zweier Vektoren
                 % d.h. : g = [4 10 18]
g = x1+x2;       % Addition zweier Vektoren
                 % d.h. : f = [5 7 9]
                 
h = [x1 x2];     % Konkatenieren zweiere Zeilenvektoren
                 % d.h. : h = [1 2 3 4 5 6]
                 
j = norm(x1);    % Länge des Vektors (Euklidische Länge)




                


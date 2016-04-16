A1 = [1 2 3; 4 5 6];      % erzeugt 2x3-Matrix 
A2 = [1 2 ; 3 4 ; 5 6];   % erzeugt 3x2-Matrix
A3 = A1';                 % A3 ist die Transponierte von A1

A4 = zeros(2, 2);   % erzeugt 2x2-Matrix mit Nullen
A5 = ones(2, 2);    % erzeugt 2x2-Matrix mit Nullen
A6 = eye(3,3);      % erzeugt 3x3-Einheitsmatrix

A7 = [1 2 3 ; 4 5 6 ; 7 8 9];

a = A7(2,3);  % Zugriff auf das Element in der 2. Zeile
              % und der 3. Spalte , d.h. a = 6
b = A7(1,:);  % Zugriff auf alle Elemente der 1 Zeile
              % d.h. : b = [1 2 3]
c = A7(:,2);  % Zugriff auf alle Elemente der 2 Spalte
              % d.h. : c = [2 5 8]' (Spaltenvektor)
              
A8 = A7(2:3,1:2); %Zugriff auf eine Submatrix 
                  % die Spalten 1 bis 2 in den Zeilen 2 bis 3
                  % d.h. : A8 = [4 5; 7 8]
A9 = [1 2 1 ; 1 0 2];
A10 = A1+A9;      % Addition zweier Matrizen 
                  % Matrizen müssen gleiche Dimensionen haben !
A11 = 2*A9;       % Multiplikation einer Matrix mit einem Skalar
A12 = A1*A9';     % Matrizenmultiplikation
                  % Matrizen müssen verkettbar sein
A13 = A1.*A9;     % Elementeweise Multiplikation 
                  % Matrizen müssen gleiche Dimensionen haben !
                  
A14 = [1 2; 3 4];
A15 = inv(A14);   % Inverse der Matrix A14
                  % Matrix muss quadratisch sein!
A16 = det(A14);   % Determinante der Matrix A15
                  % Matrix muss quadratisch sein!
                  
A17 = reshape(A1, 1, 6);  % Erzeuge aus der 2x3 Matrix A1 eine
                          % 1x6-Matrix (A17 = [1 4 2 5 3 6])
                          % Anm.: Spaltenweise Umsortierung !
A18 = A1(end:-1:1 , :);   % Zeilenreihenfolge umkehren
A19 = A1(: , end:-1:1);   % Spaltenreihenfolge umkehren
                                        

              
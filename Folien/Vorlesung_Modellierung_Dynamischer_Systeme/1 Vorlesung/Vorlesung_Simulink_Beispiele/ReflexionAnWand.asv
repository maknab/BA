p1=[13; 6];
p2=[17; 6];
v=[-1;-5];

% Berechnung von Einheitsvektoren in
% Tangential- und Normalrichtung (t und n)
t=(p2-p1)/norm(p2-p1aa);
n=[-t(2);t(1)];

% Komponenten von v in Richtung t und n
vt=v'*t;
vn=v'*n;

%Rückrechnung in Kartesische Koordinaten
vs=vt*t-vn*n

% Kontrolle (muss 0 sein)
(v-vs)'*t


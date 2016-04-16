p1=[13;6];
p2=[17;8];

v=[-5;-5];

t=(p2-p1)/norm(p2-p1);
n=[-t(2); t(1)];

vt=v'*t;
vn=v'*n;

vRefl=vt*t-vn*n
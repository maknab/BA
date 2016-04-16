
x=zeros(1000,3);
dt=0.01;

for i=1:1000
    x(i,1)=sin(pi*dt*i);
    x(i,2)=cos(pi*dt*i);
    x(i,3)=dt*i;
end
plot3( x(:,1) , x(:,2) , x(:,3));
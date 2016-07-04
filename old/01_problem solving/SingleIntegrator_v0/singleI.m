figure(1)
plot(input_signal);
hold on;
plot(output_signal);
%output_signal(1) = [];

X = con2seq(input_signal');
T = con2seq(output_signal');
lrn_net = layrecnet(1:2, 8);

lrn_net.divideParam.trainRatio = 70/100; % 70% Training
lrn_net.divideParam.valRatio = 15/100;   % 15% Validation
lrn_net.divideParam.testRatio = 15/100;  % 15% Testing

lrn_net.trainParam.show = 5;
lrn_net.trainParam.epochs = 500;
%lrn_net.trainParam.goal = 0.05;
[Xs,Xi,Ai,Ts] = preparets(lrn_net,X,T);
lrn_net = train(lrn_net,Xs,Ts,Xi,Ai);
%lrn_net = train(lrn_net,input_signal',output_signal');

%view(lrn_net);
Y = lrn_net(input_signal');
figure(2)
plot(Y)
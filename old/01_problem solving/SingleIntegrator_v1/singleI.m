figure(1)
plot(input_signal);
hold on;
plot(output_signal);

X = con2seq(input_signal');
T = con2seq(output_signal');
lrn_net = layrecnet(1:2, 8);
lrn_net.trainParam.show = 5;
lrn_net.trainParam.epochs = 250;

%% ## Version 1 ##
%% - Output falsch, da zu wenige Iterationen, stopt durch Gradient
%% - aber Datenteilung klappt
%lrn_net.divideParam.trainRatio = 70/100; % 70% Training
%lrn_net.divideParam.valRatio = 15/100;   % 15% Validation
%lrn_net.divideParam.testRatio = 15/100;  % 15% Testing
%lrn_net = train(lrn_net,input_signal',output_signal');
%view(lrn_net);
%Y = lrn_net(input_signal');
%figure(2)
%plot(Y)


%% ## Version 2 ##
%% Output richtig, aber keine Datenteilung
[Xs,Xi,Ai,Ts] = preparets(lrn_net,X,T);
lrn_net = train(lrn_net,Xs,Ts,Xi,Ai);
%view(lrn_net);
Y = lrn_net(Xs,Xi,Ai);
figure(2)
plot(cell2mat(Y))
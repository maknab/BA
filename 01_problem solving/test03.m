figure(1)
plot(input_signal);
hold on;
plot(output_signal);
%output_signal(1) = [];

X = con2seq(input_signal');
T = con2seq(output_signal');
lrn_net = layrecnet(1:2, 8);
lrn_net.trainParam.show = 5;
lrn_net.trainParam.epochs = 1000;
[Xs,Xi,Ai,Ts] = preparets(lrn_net,X,T);
lrn_net = train(lrn_net,Xs,Ts,Xi,Ai);

%view(lrn_net);
Y = lrn_net(Xs,Xi,Ai);
figure(2)
plot(cell2mat(Y))
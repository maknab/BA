% view signals
figure(1)
plot(input_signal);
hold on;
plot(output_signal);
title('Input- and Output- Signals fed to the net');

% prepare signals and net
X = con2seq(input_signal');
T = con2seq(output_signal');
lrn_net = layrecnet(1:2, 8);
lrn_net.trainParam.show = 5;
lrn_net.trainParam.epochs = 250;
[Xs,Xi,Ai,Ts] = preparets(lrn_net,X,T);

% train net
lrn_net = train(lrn_net,Xs,Ts,Xi,Ai);

% save net
save HP_net.mat lrn_net;

% view net output
Y = lrn_net(Xs,Xi,Ai);
figure(2)
plot(cell2mat(Y));
title('Output-Signal from the net');
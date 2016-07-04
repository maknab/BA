% load trained net
load HP_net.mat;

% view signals
figure(1)
plot(input_signal);
hold on;
plot(output_signal);
title('Input- and Output- Signals fed to the net');

% prepare signals
X = con2seq(input_signal');
T = con2seq(output_signal');
[Xs,Xi,Ai,Ts] = preparets(lrn_net,X,T);

% fed input signal to net
Y = lrn_net(Xs,Xi,Ai);

% view net output
figure(2)
plot(cell2mat(Y));
title('Output-Signal from the net');

% view output errors
figure(3)
plotresponse(Ts,Y)
figure(1)
plot(input_signal);
hold on;
plot(output_signal);


% p = con2seq(y);
% t = con2seq(t);
lrn_net = layrecnet(1:2, 8);
lrn_net.trainParam.show = 5;
lrn_net.trainParam.epochs = 250;
lrn_net = train(lrn_net,input_signal',output_signal');

%view(lrn_net);
Y = lrn_net(input_signal');
figure(2)
plot(Y)
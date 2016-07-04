clear;

% Parameter
order      = 4;      % Filterordnung
fg         = 12000;   % Grenzfrequenz des Filters
fs         = 24050;  % Samplefrequenz
LenNoise   = 1000;   % Anzahl der Trainings-Rauschsamples

% nichtlinearer Filter 
Nonlin     = false;
Distort    = 12;
Offset     = 0.10;

% Netzwerk-Parameter
NoOfNeurons = 2;
ActFunc     = 'satlins'; % 'purelin', 'satlins', 'tansig' 
TrainEpochs = 250;

% Rauschsignal
input_signal  = -0.7+1.4*rand(LenNoise, 1);

% Chirp-Erzeugung
t_Start = 0;       fStart  = 0.01;
t_Stop  = 10;      fStop   = 5000;

TSample = 1/fs;
% -------------------------------------------------
t = t_Start:TSample:t_Stop;
input_chirp = 0.2*chirp(t, fStart, t_Stop, fStop, 'logarithmic')';

% Tiefpass-Prototyp (normiert)
[z,p,k]   = buttap(order);  % Butterworth filter Prototyp
[bp,ap]   = zp2tf(z,p,k);   % in Übertragungsfunktion konvertieren

% Umwandeln in realen Tiefpass (mit gewünschter Grenzfrequenz)
[b,a] = lp2lp(bp,ap,fg*2*pi);

% Filter digitalisieren (Bilineare Transformation)
[b_dig,a_dig] = bilinear(b,a,fs);

% Filtern
output_signal = filter(b_dig,a_dig,input_signal);
output_chirp  = filter(b_dig, a_dig, input_chirp);


% Nichtlineare Verzerrungen
if Nonlin == true
    output_signal = (tanh(Distort*(output_signal+Offset)));
    output_chirp  = (tanh(Distort*(output_chirp+Offset)));
end

fig = figure('Name','Train In/Out');
plot(input_signal(100:400));
hold on;
plot(output_signal(100:400), 'red');
title('Input and Output (red) signal from the net');
saveas(fig, 'net_trainInOut', 'fig');

X = con2seq(input_signal');
T = con2seq(output_signal');
Xc = con2seq(input_chirp');
Tc = con2seq(output_chirp');

% ------- Netzwerk ----------------------------
lrn_net = layrecnet(1:2, NoOfNeurons);
lrn_net.layers{1}.transferFcn = ActFunc;
lrn_net.trainParam.show   = 1;
lrn_net.trainParam.epochs = TrainEpochs;

[Xs, Xi, Ai, Ts]      = preparets(lrn_net, X, T);
[Xcs, Xci, Aci, Tcs]  = preparets(lrn_net, Xc, Tc);

% Training mit Rauschen
[lrn_net, tr] = train(lrn_net,Xs, Ts, Xi, Ai);

% save various plots from training
% performance
fig = figure(2);
plotperform(tr);
saveas(fig, 'train_plotperform', 'fig');

% training state
fig = figure(3);
plottrainstate(tr);
saveas(fig,'train_plottrainstate','fig');

% error histogramm
Y = lrn_net(Xs,Xi,Ai);
e = cell2mat(Ts) - cell2mat(Y);
fig = figure(4);
ploterrhist(e,'bins',20);
saveas(fig,'train_ploterrhist','fig');

% regression
fig = figure(5);
plotregression(Ts, Y, 'Training');
saveas(fig, 'train_plotregression', 'fig');

% response
fig = figure(6);
plotresponse(Ts, Y);
saveas(fig, 'train_plotresponse', 'fig');

% save trained net
save TP_net.mat lrn_net;


%view(lrn_net);
Y = lrn_net(Xs, Xi, Ai);
fig = figure('Name','Net Train Output');
plot(cell2mat(Y));
title('Output signal from the net');
% save figure
saveas(fig, 'net_trainOutput', 'fig');


% view and save testing input
fig = figure('Name','Test Input');
plot(input_chirp);
title('Input signal fed to the net');
% save figure
saveas(fig, 'net_testInput', 'fig');

% Anwenden Network
Yc = lrn_net(Xcs, Xci, Aci);

% view test output signals
fig = figure('Name','Test Output');
subplot(2,1,1);
% semilogx(output_chirp);
plot(output_chirp);
title('Original output signal');
subplot(2,1,2);
% semilogx(cell2mat(Yc));
plot(cell2mat(Yc));
title('Net output signal');
% save figure
saveas(fig, 'net_testOnput', 'fig');

% view output errors
fig = figure(10);
Yc{end+1} = 0;
Yc{end+1} = 0;
oc = num2cell(output_chirp');
plotresponse(oc,Yc);
% save figure
saveas(fig, 'test_plotresponse', 'fig');

% NetChirp = cell2mat(Yc);
% figure(3)
% subplot(3,1,1)
% plot(NetChirp);
% title('Chirp net output');
% subplot(3,1,2)
% plot(t, output_chirp, 'red');
% title('original chirp output along t');
% subplot(3,1,3)
% 
% % Zur Laufzeitkorrektur output_chirp um 2T verzögert
% plot(output_chirp(3:size(NetChirp,2)+2) - NetChirp', 'green');
% title('output chirp Laufzeitkorrektur');


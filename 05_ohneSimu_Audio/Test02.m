% Beobachtungen
% -------------------------------------------------------------
% Je höher die Grenzfrequenz, desto leichter zu trainieren,
% da die Wirkung auf ein Eingangssignal zeitnäher zu beobachten
% ist.
%
% Für tiefe Grenzfrequenzen und Filter höherer Ordnung 
% sind Netze mit mehr Rückführungen vorteilhaft.
% 
% Bei Netzen mit mehr Rückführungen steigt die Gefahr
% von nichtlin. Verzerrungen.
% Lösung: - längere Trainingszeit
%         - Aussteuerung gegenüber Training klein halten
%
% lin. Aktivierungsfunktion mit Begrenzung scheint optimal zu sein
% (satlins). Pur lin. Aktivierungsfunktion wandert in wenigen Trainings-
% schritten gen unendlich. (hilft evtl. Regularisierung ???)

clear;

% Parameter
order      = 4;      % Filterordnung
fg         = 1400;   % Grenzfrequenz des Filters
fs         = 22050;  % Samplefrequenz
LenNoise   = 5000;   % Anzahl der Trainings-Rauschsamples

% nichtlinearer Filter 
Nonlin     = true;
Distort    = 12;
Offset     = 0.10;

% Netzwerk-Parameter
NoOfNeurons = 9;
ActFunc     = 'tansig'; % 'purelin', 'satlins', 'tansig' 
TrainEpochs = 100;

% Rauschsignal
input_signal  = -0.7+1.4*rand(LenNoise, 1);

% Chirp-Erzeugung
t_Start = 0;       fStart  = 0.01;
t_Stop  = 10;      fStop   = 5000;

TSample = 1/fs;
% -------------------------------------------------
t = t_Start:TSample:t_Stop;
input_chirp = 0.2*chirp(t, fStart, t_Stop, fStop, 'logarithmic')';

% wave-Datei einlesen
[input_wav2, fS]   = audioread('GuitarRiff.wav');
input_wav = input_wav2(:,1);

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
output_wav    = filter(b_dig, a_dig, input_wav);


% Nichtlineare Verzerrungen
if Nonlin == true
    output_signal = (tanh(Distort*(output_signal+Offset)));
    output_chirp  = (tanh(Distort*(output_chirp+Offset)));
    output_wav    = (tanh(Distort*(output_wav+Offset)));
end

figure(1)
plot(input_signal(100:400));
hold on;
plot(output_signal(100:400), 'red');


X = con2seq(input_signal');
T = con2seq(output_signal');
Xc = con2seq(input_chirp');
Tc = con2seq(output_chirp');
Xw = con2seq(input_wav');
Tw = con2seq(output_wav');


% ------- Netzwerk ----------------------------
lrn_net = layrecnet(1:2, NoOfNeurons);
% lrn_net.performParam.normalization;
% lrn_net.layers{1}.transferFcn = 'purelin';
% lrn_net.layers{1}.transferFcn = 'tansig';
% lrn_net.layers{1}.transferFcn = 'satlins';
lrn_net.layers{1}.transferFcn = ActFunc;

lrn_net.trainParam.show   = 1;
lrn_net.trainParam.epochs = TrainEpochs;

[Xs, Xi, Ai, Ts]      = preparets(lrn_net, X, T);
[Xcs, Xci, Aci, Tcs]  = preparets(lrn_net, Xc, Tc);
[Xws, Xwi, Awi, Tws]  = preparets(lrn_net, Xw, Tw);

% Training mit Rauschen
lrn_net = train(lrn_net,Xs, Ts, Xi, Ai);

%view(lrn_net);
Y = lrn_net(Xs, Xi, Ai);
figure(2)
plot(cell2mat(Y));
hold on;
plot(output_signal, 'red');


% Anwenden Network
Yc = lrn_net(Xcs, Xci, Aci);
Yw = lrn_net(Xws, Xwi, Awi);

NetChirp = cell2mat(Yc);
sizeNC   = size(NetChirp,2);
figure(3)
subplot(3,1,1)
plot(NetChirp);
subplot(3,1,2)
plot(t, output_chirp, 'red');
subplot(3,1,3)

% Zur Laufzeitkorrektur output_chirp um 2T verzögert
plot(output_chirp(3:size(NetChirp,2)+2) - NetChirp', 'green');


YWav = cell2mat(Yw);

% anhören
sound(output_wav, fS);
pause;
sound(YWav,fS);

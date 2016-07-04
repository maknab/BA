clear
addpath(genpath('./dependence')) % adds folder and subfolder
addpath([pwd '/data/'])         % pwd = identify current folder

format long

% result.resume = 0; % ???
% result.savemat = 0; % ???
result.savepic = 0;     % 0 = false, 1 = true (only  saves one of the figures)
% result.savePath = './results/'; % ???
% result.dataPath = './data/'; % ???
% result.picPath = './pic/'; % ???
result.linespec = {'r.-','b*-','go-','rs-','b^-','gd-','r--'};

speed.usegpu = 'cpu_double'; % 'gpu_single' 'gpu_double' 'cpu_single' 'cpu_double'
speed.usecluster = 0;       % 0 = false, 1 = true (waits for slaves and don't stops)
speed.numcluster = 4;       % number of clusters
speed.machine = 'lqc';      % ???
speed.gradientchecking = 0; % 0 = false, 1 = true

options.maxIter = 1e5;      % ???
options.display = 'off';    % ???
options.LS_init = 2;        % ???
options.maxFunEvals = 50;   % ???  
options.Corr = 10;          % ???

% options.optTol = 1e-66;
% options.TolX = 1e-66;
% options.TolFun = 1e-66;

problem.name = 'adding';    % used
problem.datapath = '/home/lq/timit/';
problem.numsamples = 20000;  % used             ??? % not enough
problem.batchsize = 200;     % used             ??? % not enough
problem.T = 8;              % used in netInit
problem.Ttest = problem.T;  % used in genadding (data folder
problem.numtest = 3000;     % used in genadding (data folder)
problem.gate_size = 2;      % used in netInit
problem.numMmcell = 2;      % used in netInit
problem.continualPredict = 0; % 0 = false, 1 = true
problem.bias1 = 1; % other bias     % used in netInit
problem.bias2 = 1; % output bias    % used in netInit

sgd.alpha = 0.1;            % used in main
sgd.momentum = 0.9;         % used in main
 
Main(result,speed,options,problem,sgd)
% Akkumuliert die übergebenen Werte
function y = Akkumuliere(x) 
    persistent Akku;   % Stat. Variable anl.
                      
    if isempty(Akku)   % bei Erstverwendung 
       Akku = 0;       % initialisieren
    end
    
    Akku = Akku + x;   % Akku hochzählen
    y    = Akku;       % Akku ausgeben
return


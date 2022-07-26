# MusicEmotion
MusicEmotion rappresenta un'ontologia in grado di descrivere e modellare l'ascolto di una canzone e la reazione emotiva che suscita nell'ascoltatore.

## Report
Questo progetto è stato svolto per il corso di Web Semantico dell'Università di Bologna, campus di Cesena (IT).

Dai un'occhiata alla [documentazione](https://github.com/Mosgheo/MusicEmotion/blob/main/doc/MusicEmotion.pdf) del progetto.

## Deployment
Questo elaborato è stato realizzato tramite Stardog, l’esecuzione di questo IDE necessita la presenza di un server Stardog sul quale mantenere il database. Requisiti:
* **Docker**
* **licenza d’uso di Stardog** (ottenibile [qui](https://www.stardog.com/get-started/) e già inclusa all’interno della cartella ”licenze”).

Step per l'installazione:
* Download dell’immagine di Stardog tramite Docker Hub:
<pre><code>docker pull stardog/stardog:latest</code></pre>
* Lanciare il server Stardog:
<pre><code>docker run -it -v ~/stardog-home/:/var/opt/stardog -p 5820:5820 stardog/stardog</code></pre>

”stardog-home” è il percorso della cartella all’interno della quale è presente solamente la licenza d’uso di Stardog ottenuta precedentemente; diventerà poi la directory contenente i file necessari all’esecuzione del server;

* Collegarsi a [Stardog Studio](https://stardog.studio/#/) e connettersi al server precedentemente istanziato:
    * **indirizzo**: [http://localhost:5820/](http://localhost:5820/);
    * **ID**: admin;
    * **password**: admin.

* Spostarsi nella sezione **Databases** e creare il database ”MusicEmotion”;

* All’interno della sezione ”Databases”, dopo aver selezionato il database appena creato, sotto la voce **Namespaces** importare tramite la funzione ”Import” in alto a sinistra il file ”EmotionMusic-namespaces.ttl”;

* Spostarsi nella sezione **Models** e tramite la funzionalità ”Create Model” creare un modello con le seguenti caratterstiche:
    * **Model Name**: EmotionsInMusic-Ontology;
    * **Named Graph**: musicemotions:ontology:graph;
    * **Prefix**: music.

* Ritornare nella sezione ”Databases”, selezionare il database appena creato e, tramite la funzione Load data importare il file EmotionMusic-Ontology.ttl specificando l’opzione **Load Data** to musicemotions:ontology:graph, ovvero il grafo appena creato. A questo punto tale grafo contiene l’ontologia;

* Per popolare il database, selezionarlo dalla sezione ”Databases” e tramite la funzionalità ”Load data” importare tutti i file presenti nella cartella **utils**, lasciando l’opzione ”**Load Data to Default Graph**”;

* Per aggiungere le query, spostarsi nella sezione Workspace ed effettuare l’upload dei file cliccando in alto a destra sul ”+”.

## Authors
* [Luca Salvigni](https://github.com/Mosgheo)
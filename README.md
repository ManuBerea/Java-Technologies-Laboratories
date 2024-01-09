# Tehnologii_Java_2023_2024

# Tema 1:
Clasa RandomTreeServlet reprezinta un servlet Java care procesează cereri HTTP pentru generarea unui arbore aleatoriu. Utilizatorul poate introduce numărul de vârfuri printr-un formular web, iar rezultatul, o matrice de adiacență, este afișat direct în browser sub formă de tabel HTML. În spate, servlet-ul folosește metodele doGet pentru afișarea formularului și doPost pentru procesarea datelor și afișarea rezultatului. În plus, servlet-ul loghează detalii importante despre cerere, cum ar fi metoda HTTP, adresa IP a clientului și parametrii cererii.

# Tema 2:
Aplicația este structurată pe baza unui model orientat pe obiect, utilizând clasele Input și Output pentru a gestiona datele de intrare și ieșire. 
Clasa Input se ocupă de stocarea fișierului încărcat, în timp ce clasa Output procesează datele grafului și calculează proprietățile cerute (Ordinul Grafului, Mărimea Grafului si Numărul de Componente Conexe), aceasta clasa reprezentand si logica de business a aplicatiei. 
Fluxul web este gestionat de servletul ProcessingServlet, care prelucrează cererile de încărcare a fișierelor, proceseaza datele cu ajutorul clasei Output și redirecționează rezultatele către pagina "result.jsp". 
Filtrul web RequestLoggingFilter înregistrează toate cererile HTTP primite de servlet si le afiseaza in Log.

# Tema 3:
Există două clase principale: ProjectBean și StudentBean, fiecare responsabilă pentru manipularea datelor și logica de business asociată proiectelor, respectiv studenților. 
ProjectBean: Se ocupă de crearea, ștergerea și afișarea proiectelor. Proiectele sunt stocate într-o bază de date, iar clasa interacționează cu aceasta prin JDBC pentru a executa operațiunile necesare. Utilizatorii pot adăuga proiecte noi, vizualiza toate proiectele existente și șterge proiecte existente.
StudentBean: Similar cu ProjectBean, această clasă gestionează studenții, oferind funcționalități pentru adăugarea, ștergerea și afișarea studenților. De asemenea, oferă funcționalitate pentru afișarea proiectelor asociate unui student și adăugarea unui proiect unui student.
Interfața utilizator este gestionată prin pagini XHTML, care utilizează JSF pentru a afișa datele și a interacționa cu utilizatorul. Se folosesc componente specifice JSF, cum ar fi tabele de date, formulare modale și componente pentru selecția datelor.

# Tema 4:
Această temă combină crearea și structurarea paginilor web cu JSF și template-uri XHTML, și implementarea eficientă a conexiunilor la baza de date. 
Pagina principală "page.xhtml" stabilește structura generală a site-ului, incluzând un "header", conținutul și un "footer" prin template-uri separate. 
"dataView.xhtml" este folosită pentru vizualizarea datelor în liste sau tabele, iar "dataEdit.xhtml" pentru editarea datelor printr-un formular sau dialoguri modale.
Conexiunile la baza de date sunt gestionate prin utilizarea unui "connection pool" și a obiectelor de tip DataSource, configurate și injectate în bean-urile StudentBean si ProjectBean, care accesează și procesează datele. 

# Tema 5:
Această temă demonstrează implementarea unei aplicații web bazată pe Java EE, folosind specificațiile JPA pentru gestionarea persistenței datelor. Aplicația definește două entități principale, Project și Student, fiecare cu propriile clase, controlere și repository-uri. Aceste entități sunt mapate la o bază de date prin JPA, utilizând anotări pentru a defini relațiile și query-urile. Clasa persistence.xml configurează unitatea de persistență și sursa de date JDBC. Controlerele, ProjectController și StudentController, sunt injectate cu repository-uri corespunzătoare, folosind EJB, pentru a gestiona operațiunile CRUD asupra entităților. Repository-urile utilizează EntityManager pentru interacțiuni cu baza de date. În plus, aplicația include named queries JPA-QL pentru operațiuni specifice, utilizând anotarea @NamedQueries. De exemplu, în clasa Project, există named queries pentru a găsi toate proiectele (Project.findAll) și pentru a șterge un proiect după ID (Project.deleteById). 

# Tema 6:
Această temă vizează restructurarea stratului de acces la date al aplicației existente, folosind EJB pentru a implementa repository-uri și gestionarea tranzacțiilor. Se folosesc trei tipuri de bean-uri: un stateless session bean pentru verificarea disponibilității proiectelor, un stateful session bean pentru asignarea proiectelor și un singleton session bean care menține o hartă în memorie a asignărilor curente. Modificările în modelul aplicației permit ca un student să primească mai multe proiecte, în timp ce un proiect poate fi asignat la cel mult un student. Pagina "assign" permite asignarea manuală a proiectelor, integrând logica de business prin bean-urile EJB. Tranzacțiile sunt gestionate de EJB, asigurând atomicitatea asignărilor: toate proiectele sunt asignate cu succes sau tranzacția este inversată. Singleton bean-ul actualizează harta asignărilor la fiecare schimbare, oferind o viziune centralizată asupra stării curente a asignărilor.

# Tema 7:
Aceasta tema continua laboratorul anterior si implementeaza un serviciu Web RESTful pentru gestionarea orarelor, utilizând JAX-RS și JPA. 
Prin clasa ApplicationConfig, se configurează serviciile RESTful ale aplicației și se stabilește calea de bază "/api". 
Clasa TimetableSubmissionService definește endpoint-urile RESTful pentru operațiuni CRUD asupra orarelor (metodele createTimetable, updateTimetable, deleteTimetable, getAllTimetables și getTimetablesByUserId implementează adăugarea, actualizarea, ștergerea și citirea datelor). Serviciile produc și consumă date de tip JSON. De asemenea se folosesc anotări OpenAPI pentru a documenta serviciile, cum ar fi @ApiOperation și @ApiResponses. 
TimetableController gestionează interacțiunea cu interfața utilizatorului și apelează serviciile RESTful, utilizând un client JAX-RS pentru a comunica cu TimetableSubmissionService.
Aplicația include și un filtru de cache (CacheSubmissionsFilter), care eficientizează gestionarea cererilor GET prin stocarea temporară a datelor și resetează cache-ul la modificări (POST, PUT, DELETE). Acesta accelerează accesul la informații, servind datele direct din cache odată ce acesta este populat.

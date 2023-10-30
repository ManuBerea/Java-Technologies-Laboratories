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

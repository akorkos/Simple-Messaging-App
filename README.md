# Assignment in Communication Networks 

### Πληροφορίες
Η εργασία στο μάθημα [Δίκτυα Επικοινωνιών [NCO-05-02]](https://elearning.auth.gr/course/view.php?id=5942) του τμήματος 
πληροφορικής του Α.Π.Θ για το ακαδημαϊκό έτος 2022/23. <br/>

Στα πλαίσια αυτής της εργασίας αναπτύχθηκε ένα κατανεμημένο σύστημα ανταλλαγής μηνυμάτων που χρησιμοποιεί ένα απλό 
request-reply protocol. Οι clients στέλνουν στον Server ένα Request και έπειτα ο Server απαντάει με ένα Response και η 
σύνδεση τους τερματίζει. Σαφέστερα, χρησιμοποιήθηκε η τεχνολογία Sockets (Sockets-I/O streams-Threads).

Σε αυτό το σύστημα διαφορετικοί χρήστες θα μπορούν να δημιουργήσουν accounts και να στείλουν μηνύματα μεταξύ τους, 
καθώς ο εξυπηρετητής θα τα διαχειρίζεται. Τη λειτουργικότητα αυτή θα τους την παρέχουν:
* ένα πρόγραμμα εξυπηρετητή, το οποίο θα έχει την ικανότητα να διαχειριστεί ταυτόχρονα πολλαπλές αιτήσεις από πελάτες.
* προγράμματα πελάτη, κάθε ένα από τα οποία θα έχει την ικανότητα να στέλνει αιτήσεις στον εξυπηρετητή.

### Υλοποίηση

Η δομή της εργασίας, φαίνεται απο το παρακάτω διάγραμμα UML:

![UML Diagram](/uml.png)

Το πακέτο `Client` υλοποιεί τις απαραίτητες δομές για τον χρήστη. Σαφέστερα, η κλάση `Client` είναι αυτή που εκτελεί τα
αιτήματα προς τον `Server`. Δέχεται τα εξής ορίσματα: `ip, port_number, fid, args` για την εκτέλεση των αιτημάτων. Επιπλέον,
το πακέτο περιεχει και την κλάση `RequestHandler` όπου καλείται απο την κλάση `Client` και αναλαμβάνει να ανοίξει την
σύνδεση με τον `Server`, να αποσταλεί το αίτημα στον `Server` και τέλος να κλείσει τη σύνδεση με τον `Server`.

Το πακέτο `Common`, περιεχει μόνο μια κλάση (`Request`) όπου αναπαριστά τα αιτήματα. Χρησιμοποιείται και στο πακέτου του
`Client` αλλά και στου `Server`, για την αποστολή των αιτημάτων και την επεξεργασία τους αντίστοιχα.

Το πακέτο `Server` υλοποιεί τις απαραίτητες δομές που απαιτεί ο `Server`. Αρχικά, η κλάση `Server` δέχεται μόνο ενα όρισμα
το `port_number` οπου δηλώνει την πόρτα στην οποία θα ακούει αιτήσεις. Η κλάση `ServerHandler`, αναλαμβάνει τον χειρισμό 
των λειτουργιών του `Server`, όπως την ταυτοποίηση ενός χρηστή κτλ. Η εκτέλεση των αιτημάτων κάθε χρηστή, επιτυγχάνεται μέσω 
της κλάσης `ClientHandler` που καλείται απο την `ServerHandler` που είναι εφοδιασμένη με μεθόδους για την εμφάνιση μιας λίστας 
με όλα τα accounts που υπάρχουν στο σύστημα, την αποστολή ενός μηνύματος κτλ. 
Επιπλέον, το πακέτο περιεχει και τις κλάσεις `Account, MessageBox, Message` που αναπαριστούν του χρηστές, την θυρίδα μηνυμάτων
κάθε χρηστή και κάθε μήνυμα αντίστοιχα.

#### Λεπτομέρειες / παραδοχές

* Ο `Server` τρέχει ατέρμονα, μέχρι να κλείσει το παράθυρο στο οποίο τρέχει,
* το ID ενός χρήστη, αποτελείται απο 4 τυχαία αριθμητικά ψηφία (δηλ. [0000, 9999]),
* το ID ενός μηνύματος, αποτελείται απο 4 τυχαία αλφαριθμητικά ψηφία (π.χ. gf9U),
* εαν δεν δοθούν σε κάποια εκτέλεση του `Client` τα απαραίτητα ορίσματα τότε δεν εμφανίζεται τίποτα στην οθόνη

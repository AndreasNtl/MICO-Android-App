# Project Name: MiCo

### Developers:

*   Χρήστος 		 
*   Ανδρέας 		 
*   Λεονάρδος  

---

### Περιγραφή

<p> Η παρούσα εργασία εκπονήθηκε για τις ανάγκες του μαθήματος "Ανάπτυξη Λογισμικού για Συστήματα Δικτύων και Τηλεπικοινωνιών" και σύμφωνα με τις οδηγίες 
της κυρίας Αλωνιστιώτη και των βοηθών του μαθήματος. Υλοιποιήθηκε μια εφαρμογή, controllerproject, Java στο IDE περιβάλλον IntelliJ και μια εφαρμογή, clientproject,
 Android στο περιβάλλον Android Studio. Οι δύο εφαρμογές επικοινωνούν μεταξύ τους μέσω MQTT server σύμφωνα με την λογική publisher/subscriber, όπου ο χρήστης, client
κάνει subscribe σε ένα topic και ο publisher κάνει publish στο topic και στέλνει ενα μήνυμα στους εγγεγραμμένους στο topic χρήστες. </p>

<p> Στην Java υλοποιήθηκε GUI όπου μέσω των κουμπιών ο publisher μπορεί να στείλει μηνύματα στον subscriber για να ανοίξει/κλείσει το flash και την μουσική στην 
android συσκευη για κάποια δευτερόλεπτα. Υπάρχει και η επιλογή Automatic mode όπου στέλνοντα τα προαναφερθέντα μηνύματα τυχαία και για τυχαία διάρκεια. 
Επίσης, υπάρχει κουμπί pattern όπου ο φακός στέλνει σε κώδικα Morse το μήνυμα που πληκτρολογεί ο controller. </p>

<p> Στην εφαρμογή Android υλοποιήθηκαν τα 4 κουμπιά, όπως και στην Java, στο main Activity. Με την χρήση του back button εμφανίζεται pop-up confirmation dialog box.
 Στο Μενού, ύπαρχουν τα Settings και η επιλογή για Quit. Στα Settings ο χρήστης μπορεί να αλλάξει τα στοιχεία σύνδεσης της εφαρμογής με τον MQTT server, δηλαδή
 την διεύθυνση IP και το port, τα οποία αποθηκεύονται (shared preferences) κατά την επανεκκίνηση της εφαρμογής. </p>
 
<p> Επιπλέον για το δεύτερο παραδοτεό υλοποιήθηκαν 2 ακόμα κουμπιά στο τερματικό Android, τα οποία αλλάζουν το mode σε automatic ή manual, που ουσιαστικά δίνεται
 στον χρήστη να κάνει χειροκίνητα μόνος του subscribe στον MQTT server και όταν προσπάθει να κάνει subscribe εμφανίζεται επίσης progress bar. </p>

<p> Για κάθε αρχείο .csv υπολογίζεται η εντροπία των 14 καναλιών του, χρησιμοποιώντας το αρχείο calculateEntropy.java, και σύγκρινουμε το διάνυσμα που βρίσκουμε
 με τους Κ πιο κοντινούς γείτονες απο το Training Set και ύστερα ανάλογα με την ευκλείδια τους απόσταση, κατηγοριοποιούμε το διάνυσμα σαν Eyes_Open ή Eyes_Closed και 
 ανάλογα αν το αρχικό αρχείο ήταν Eyes_Open/Closed μετράμε τις επιτυχιές και τις αποτυχίες για να πάρουμε το τελικό ποσοστό. </p>
 
<p> Προστέθηκαν 2 κουμπιά Start/Stop Classification στο γραφικό μενού του controller με τα οποία ξεκινάει η διαδικασία του classification algorithm και
 δίνονται τα αποτελέσματα όταν τελειώσει ο αλγόριθμος ή νωρίτερα αν το σταματήσει ο controller με το Stop Classification κουμπί. </p>
 
<p> Τα αποτελέσματα που παίρνουμε έπειτα από το classification algorithm κυμαίνονται ανάλογα με την τιμή του Κ, με το καλύτερο ποσοστό επιτυχίας να είναι 74.3%
  για Κ = 11. Επίσης, προσέξαμε πως 6 από τα αρχεία .csv είναι άδεια οπότε τα εξαιρέσαμε από τον αλγόριθμο για να πετύχουμε καλύτερο ποσοστό επιτυχίας.
  Τέλος, παρατηρούμε πως για τιμές του Κ ψηλότερες από 15 το ποσοστό επιτυχίας πέφτει. </p>

---

### Τεχνικές Λεπτομέρειες

<p> Οι εφαρμογές υλοποιήθηκαν και δοκιμάστηκαν στα προσωπικά μας μηχανήματα, συγκεκριμένα η εφαρμογή Java σε PC/laptop και η εφαρμογή Android έτρεχε σε κινητό
 με λειτουργικό Android 7.0, API level 24. Για την δημιουργία GUI στην Java χρησιμοποιήθηκε το Swing toolkit.</p>

---

### Screenshots

<p> Φωτογραφίες/Στιγμιότυπα που δείχνουν την λειτουργικότητα του προγράμματος. </p>

* [Album φωτογραφιών](https://imgur.com/a/Xkkqb)

--- 

### References

<p> Links από τα οποία χρησιμοποιήθηκαν κομμάτια από κώδικα και μας βοήθησαν για την ολοκλήρωση της εργασίας. </p>

* [Για το testing του MQTT server, publisher/subscriber μηνύματα](https://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm-that-the-user-wishes-to-exit-an-android-activity)
* [Για το confirmation dialog box on quit](https://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm-that-the-user-wishes-to-exit-an-android-activity)
* [Για την λειτουργία του φακού](http://www.theappguruz.com/blog/flash-light-demo) 
* [Για την αναπαραγωγή μουσικής](https://www.mkyong.com/android/how-to-turn-onoff-camera-ledflashlight-in-android/)
* [Official Tutorial της Oracle για GUI περιβάλλον](https://docs.oracle.com/javase/tutorial/uiswing/index.html)
* [Για το άνοιγμα και διάβασμα των αρχείων .csv](https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java)
* [Για την παρακολούθηση και έλεγχο της διαθεσιμότητας δικτύου/internet](https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html)

Podsumowanie:

# proszę o użycie najnowszych bibliotek
UI: Ze względu, że nie czuję się pewnie w świecie javascriptowym, nie zmieniałem wersji żadnej z bibliotek w przykładowym projekcie. Generalnie wychodzę z założenia, że jak coś działa a nie do końca jestem pewien jak to usprawnić to nie ruszam
API: Podbiłem wersję javy do 11 oraz spring boota do najnowszego. Dodałem również spock + groovy do testowania aplikacji

# proszę dostosować kod do własnych praktych programistycznych 
UI: Tutaj jak wyżej nic nie zmieniałem
API: Przeorganizowałem cały kod na podejście "pakietowe" aktualnie w pakiecie employee znajdują się klasy publiczne oraz pakietowe. Klasami pakietowymi są klasy których nie chciałem używać na zewnątrz pakietu. Dlaczego? Ponieważ 
1) Dużo łatwiej się testuje tylko jeden pkt wejścia do pakietu (EmployeeService) 
2) Migracja danego pakietu na mikroserwis jest bezbolesna, ponieważ mając dostęp do klasy tylko poprzez pakiet jestem w 100% pewien, że nikt mi tych klas nie użyje w innym miejscu w kodzie przez co bez problemu będę mógł je wynieść do innego mikroserwisu.

Exception handler dla globala exception ukryłem message, takie rzeczy nie powinny wyświetlać się w odpowiedzi, tylko być logowane w aplikacji. 

Usunałem exception dla resourceNotFound ponieważ identyczny mechanizm oferuje ResponseEntity.of gdy resource jest Optional.empty zwraca 404

# proszę o zmianę MySql na MongoDB
API: baza zmieniona z mysql na mongodb

# wyłączamy Hibernate z projektu
API: jak wyżej

# Wprowadzamy walidację na numer telefonu w USA
UI: dodano nowe pole numer telefonu oraz jego walidację. Dodałem również walidację na pozostałe pola
API: dodano nowe pole numer telefonu oraz jego walidację. Dodałem również walidację na pozostałe pola
Generalnie jestem zwolennikiem ograniczenia interakcji ui z api. Dlatego w tym przypadku waliadacje są w dwóch miejscach.
 
# dodajemy logi do aplikacji aby można było udowodnić co kto robił na aplikacji
API - dodałem logi na poziomie controllera, także każde wejście do aplikacji przez api jest logowane

# opcjonalnie do Angular można dodać unit test 
UI: jako że pkt był opcjonalny nie wykonałem go

# Dodatkowo
- wprowadziłem dockera w celu zminimalizowania ryzyka "u mnie działa" oraz nie instalowania milionów dodatkowych serwis itd.. Aby odpalić aplikację wystarczy mnieć zainstalowanego docker i docker-compose i w głównym katalogu wywołać następującą komendę: docker-compose up --build -d

- Zauważyłem, że jest przygotowany szkielet na update, ale nie jest przygotowany UI pod to, nie ruszałem tego, bo nie było nic o tym w wymaganiach



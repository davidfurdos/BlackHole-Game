Fekete lyuk

Készítsünk programot, amellyel a közismert amőba játék következő változatát
játszhatjuk. Adott egy n × n-es tábla, amelyen a két játékos űrhajói helyezkednek el, középen pedig egy
fekete lyuk. A játékos 𝑛−1 űrhajóval rendelkezik, amelyek átlóban helyezkednek el a táblán (az azonos
színűek egymás mellett, ugyanazon az oldalon).
A játékosok felváltva léphetnek. Az űrhajók vízszintesen, illetve függőlegesen mozoghatnak a
táblán, de a fekete lyuk megzavarja a navigációjukat, így nem egy mezőt lépnek, hanem egészen addig
haladnak a megadott irányba, amíg a tábla széle, a fekete lyuk, vagy egy másik, előtte lévő űrhajó meg
nem állítja őket (tehát másik űrhajót átlépni nem lehet). Az a játékos győz, akinek sikerül űrhajóinak
felét eljuttatnia a fekete lyukba.
A program biztosítson lehetőséget új játék kezdésére a táblaméret megadásával (5×5, 7×7, 9×9),
és ismerje fel, ha vége a játéknak. Ekkor jelenítse meg, melyik játékos győzött, majd automatikusan
kezdjen új játékot.

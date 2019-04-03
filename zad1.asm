
data1 segment


	wejscie db 30,?,30 dup (?)
	
	liczba1 db 10 dup (?)
	liczba2 db 10 dup (?)
	operator db 10 dup (?)
	
	
	liczbaint1 db 11
	liczbaint2 db 11
	
	wynikint dw 100
	;wynikintodejm db 100
	
	; stale teksty w programie:
	powitanie db "Wprowadz slowny opis dzialania:", 10,13, '$' 
	wypisz_wynik db "Wynikiem jest:", 10, 13 , '$'
	bledne_dane db "Podano niepoprawne dane!", 10, 13 , '$'
	
	; stale liczby  i operatory w programie:
	
	dodac db "dodac$"
	odjac db "odjac$"
	razy db "razy$"
	
	minus db "minus$"	; znak przy wyniku
	
	zero db "zero$"
	jeden db "jeden$"
	dwa db "dwa$" 
	trzy db "trzy$" 
	cztery db "cztery$"
	piec db "piec$"
	szesc db "szesc$"
	siedem db "siedem$"
	osiem db "osiem$"
	dziewiec db "dziewiec$"
	
	dziesiec db "dziesiec$"
	jedenascie db "jedenascie$"
	dwanascie db "dwanascie$"
	trzynascie db "trzynascie$"
	czternascie db "czternascie$"
	pietnascie db "pietnascie$"
	szesnascie db "szesnascie$"
	siedemnascie db "siedemnascie$"
	osiemnascie db "osiemnascie$"
	dziewietnascie db "dziewietnascie$"
	
	dwadziescia db "dwadziescia$"
	trzydziesci db "trzydziesci$"
	czterdziesci db "czterdziesci$"
	piecdziesiat db "piecdziesiat$"
	szescdziesiat db "szescdziesiat$"
	siedemdziesiat db "siedemdziesiat$"
	osiemdziesiat db "osiemdziesiat$"
	
	spacja db " $"
	nic db "$"
	
data1 ends


code1 segment

start1:
;==========================main===========================
	mov ax, data1
	mov ds,ax	;przenies seg data1 do ds
	
	call witaj
	
	call zczytaj
	
 	call zparsuj_wejscie	
	
	call zparsuj_slowa_do_liczb
	
	call wypisanie_wyniku
	
	call policz
	
	call zparsuj_wynik
	
	


	
koniec:	;zakonczenie programu
	mov ah,4ch
	int 21h
;======================================funkcje==============================

zparsuj_wynik:; tutaj program porowna wynik liczbowy i sprawdzi na jaki string go zamienic 
	cmp word ptr ds:[wynikint],100	;jesli wynikint wynosi 100, tzn ze nie byl zmieniony on tylko wynik bitowy ktory juz zostal wypisany 
	je koniec
	
	cmp word ptr ds:[wynikint], 20 ;sprawdzam czy wynik jest mniejszy od 20
	jl parsuj_poj
		
	parsuj_pod: ; jesli wynik jest wiekszy od/rowny 20 to parsuje sprawdzajac wynik dzielenia przez 10 i jego reszte
		mov ax, word ptr ds:[wynikint]	;wrzucam wynik do ax,
		mov bl,10 
		div bl								; dziele wynik przez 10, wynik do al, reszta do ah
		mov bl,ah ;	wurzcamy reszte z ah do bl, bo ah jest nadpisywane w print
		
	sprawdz_dziesiatki:
		cmp al, 2			;sprawdza czy wnyik dzielenia bedacy w al jest rowny 2,3...itp
		je wynik_2d
		cmp al, 3
		je wynik_3d
		cmp al, 4
		je wynik_4d
		cmp al, 5
		je wynik_5d
		cmp al, 6
		je wynik_6d
		cmp al, 7
		je wynik_7d
		cmp al, 8
		je wynik_8d
	
	wynik_2d:		;zapisuje dwadziescia do dx i wypisuje je na ekran ...
		mov dx, offset dwadziescia
		call print 
		jmp sprawdz_jednosci
	wynik_3d:		
		mov dx, offset trzydziesci
		call print 
		jmp sprawdz_jednosci
	wynik_4d:		
		mov dx, offset czterdziesci
		call print 
		jmp sprawdz_jednosci
	wynik_5d:		
		mov dx, offset piecdziesiat
		call print 
		jmp sprawdz_jednosci
	wynik_6d:		
		mov dx, offset szescdziesiat
		call print 
		jmp sprawdz_jednosci
	wynik_7d:		
		mov dx, offset siedemdziesiat
		call print 
		jmp sprawdz_jednosci
	wynik_8d:		
		mov dx, offset osiemdziesiat
		call print 
		jmp sprawdz_jednosci
	
	
	call wypisz_blad ; wyrzucenie bledu w razie zlego wyniku, bo nie powinnismy dojsc w to miejsce
	
	sprawdz_jednosci:	;tutaj porownamy ah i sprawdzimy jaka mamy cyfre jednosci 
		
		
		mov dx, offset spacja 
		call print		;wypisz spacje miedzy slowami
		
		mov ah,bl	;przywracamy ah poprzedni wynik z bl do ah (reszte z dzielenia)
		
		cmp ah,0
		je koniec_parsuj_wynik	;jesli ah to zero to konczymy procedure 
		cmp ah, 1
		je wynik_1
		cmp ah, 2
		je wynik_2
		cmp ah, 3
		je wynik_3
		cmp ah, 4
		je wynik_4
		cmp ah, 5
		je wynik_5
		cmp ah, 6
		je wynik_6
		cmp ah, 7
		je wynik_7
		cmp ah, 8
		je wynik_8
		cmp ah, 9
		je wynik_9
	
	call wypisz_blad ; wyrzucenie bledu w razie zlego wyniku, bo nie powinnismy dojsc w to miejsce
	
	parsuj_poj: ; jesli wynik jest mniejszy niz 20 to parsuje go po prostu porownujac
	
	cmp word ptr ds:[wynikint], 19
	je wynik_19
	cmp word ptr ds:[wynikint], 18
	je wynik_18
	cmp word ptr ds:[wynikint], 17
	je wynik_17
	cmp word ptr ds:[wynikint], 16
	je wynik_16
	cmp word ptr ds:[wynikint], 15
	je wynik_15
	cmp word ptr ds:[wynikint], 14
	je wynik_14
	cmp word ptr ds:[wynikint], 13
	je wynik_13
	cmp word ptr ds:[wynikint], 12
	je wynik_12
	cmp word ptr ds:[wynikint], 11
	je wynik_11
	cmp word ptr ds:[wynikint], 10
	je wynik_10
	cmp word ptr ds:[wynikint], 9
	je wynik_9
	cmp word ptr ds:[wynikint], 8
	je wynik_8
	cmp word ptr ds:[wynikint], 7
	je wynik_7
	cmp word ptr ds:[wynikint], 6
	je wynik_6
	cmp word ptr ds:[wynikint], 5
	je wynik_5
	cmp word ptr ds:[wynikint], 4
	je wynik_4
	cmp word ptr ds:[wynikint], 3
	je wynik_3
	cmp word ptr ds:[wynikint], 2
	je wynik_2
	cmp word ptr ds:[wynikint], 1
	je wynik_1
	cmp word ptr ds:[wynikint], 0
	je wynik_0
	cmp word ptr ds:[wynikint], -1
	je wynik_1
	cmp word ptr ds:[wynikint], -2
	je wynik_2
	cmp word ptr ds:[wynikint], -3
	je wynik_3
	cmp word ptr ds:[wynikint], -4
	je wynik_4
	cmp word ptr ds:[wynikint], -5
	je wynik_5
	cmp word ptr ds:[wynikint], -6
	je wynik_6
	cmp word ptr ds:[wynikint], -7
	je wynik_7
	cmp word ptr ds:[wynikint], -8
	je wynik_8
	cmp word ptr ds:[wynikint], -9
	je wynik_9
	
	
	call wypisz_blad ; wyrzucenie bledu w razie zlego wyniku, bo nie powinnismy dojsc w to miejsce
	
	wynik_19:
		mov dx, offset dziewietnascie	;przesuwam offset stringa "dziewietnascie" do dx i wywoluje procedure print...
		call print 
		jmp koniec_parsuj_wynik	;skacze na koniec procedury parsowania wyniku
	wynik_18:
		mov dx, offset osiemnascie
		call print 
		jmp koniec_parsuj_wynik
	wynik_17:
		mov dx, offset siedemnascie
		call print 
		jmp koniec_parsuj_wynik
	wynik_16:
		mov dx, offset szesnascie
		call print 
		jmp koniec_parsuj_wynik
	wynik_15:
		mov dx, offset pietnascie
		call print 
		jmp koniec_parsuj_wynik
	wynik_14:
		mov dx, offset czternascie
		call print 
		jmp koniec_parsuj_wynik
	wynik_13:
		mov dx, offset trzynascie
		call print 
		jmp koniec_parsuj_wynik
	wynik_12:
		mov dx, offset dwanascie
		call print 
		jmp koniec_parsuj_wynik
	wynik_11:
		mov dx, offset jedenascie
		call print 
		jmp koniec_parsuj_wynik
	wynik_10:
		mov dx, offset dziesiec
		call print 
		jmp koniec_parsuj_wynik
	wynik_9:
		mov dx, offset dziewiec
		call print 
		jmp koniec_parsuj_wynik
	wynik_8:
		mov dx, offset osiem
		call print 
		jmp koniec_parsuj_wynik
	wynik_7:
		mov dx, offset siedem
		call print 
		jmp koniec_parsuj_wynik
	wynik_6:
		mov dx, offset szesc
		call print 
		jmp koniec_parsuj_wynik
	wynik_5:
		mov dx, offset piec
		call print 
		jmp koniec_parsuj_wynik
	wynik_4:
		mov dx, offset cztery
		call print 
		jmp koniec_parsuj_wynik
	wynik_3:
		mov dx, offset trzy
		call print 
		jmp koniec_parsuj_wynik
	wynik_2:
		mov dx, offset dwa
		call print 
		jmp koniec_parsuj_wynik
	wynik_1:
		mov dx, offset jeden
		call print 
		jmp koniec_parsuj_wynik
	wynik_0:
		mov dx, offset zero
		call print 
		jmp koniec_parsuj_wynik
	
		
	koniec_parsuj_wynik:
		
	ret

policz: ;tutaj program sprawdzi jaki ma byc uzyty operator i czy jest poprawny
	
	mov si, offset operator	;sprawdza czy to operator dodawania
	mov di, offset dodac
	
	call porownaj_stringi	;porownanie stringow
	

	cmp ah, 1		;sprawdza flage wrzucona na ah- czy stringi byly rowne 
	je policz_dodac 		;jesli flaga byla 1 [stringi rowne] to wykonaj operacje
	
	
	mov si, offset operator	;sprawdza czy to operator odejmowania
	mov di, offset odjac
	
	call porownaj_stringi	;porownanie stringow


	cmp ah, 1		;sprawdza flage wrzucona na ah- czy stringi byly rowne 
	je policz_odjac

	mov si, offset operator	;sprawdza czy to operator dodawania
	mov di, offset razy
	
	call porownaj_stringi	;porownanie stringow
	

	cmp ah, 1		;sprawdza flage wrzucona na ah- czy stringi byly rowne 
	je policz_razy

	jmp wypisz_blad ;jesli to zaden z poprawnych operatorow, wypisz blad i zakoncz program 
	
	
	policz_dodac:		;jesli operatorem bylo dodawanie to:
	
	mov al, byte ptr ds:[liczbaint1]	;przenosi liczby do al i ah
	mov ah, byte ptr ds:[liczbaint2]
	
	add al,ah 								;dodaje rejestry do siebie i zapisuje w al
	
	mov ah,0							;ustawiam ah na 0 aby moc wstawic  ax  jako wynik
	mov word ptr ds:[wynikint],ax 		;przenosi wartosc z ax do wyniku

	jmp koniec_policz
	
	policz_odjac: ;jesli operatorem bylo odejmowanie to:
	
	mov al, byte ptr ds:[liczbaint1]    ;przenosi liczby do al i ah
	mov bl, byte ptr ds:[liczbaint2]	
	
	mov ah,0				;ustawiamy ah i bh na 0 aby w ax i bx byla liczba al i bl
	mov bh,0 
	
	sub ax,bx								;odejmuje rejestry od siebie i zapisuje w al
	
	mov word ptr ds:[wynikint],ax 		;przenosi wartosc z al do wyniku - taki rozkaz bo al to bajt a wynikint to slowo	
	
	js	wynik_ujemny					;jesli odejmowanie sprawilo ze liczba<0 to wypisz minus na ekran
	
	jmp koniec_policz				;jesli wynik nieujemny to id

	policz_razy: ;jesli operatorem bylo mnozenie to:
	
	mov al, byte ptr ds:[liczbaint1]	;przenosi liczby do al i ah
	mov ah, byte ptr ds:[liczbaint2]
	
	mul ah 								;mnozy rejestr al z ah i wynik zapisuje w ax 
	
	mov word ptr ds:[wynikint],ax 		;przenosi wartosc z al do wyniku
	
	jmp koniec_policz
	
	koniec_policz:

	ret
wynik_ujemny:; wypisuje minusa 
		mov dx, offset minus	;wypisuje slowo "minus"
		call print
		mov dx, offset spacja	;wypisuj spacje
		call print 				;wraca na koniec liczneia
		jmp koniec_policz

zparsuj_slowa_do_liczb: ;tutaj program porowna obie liczby zapisane jako stringi z danymi w programie 
;i sprobuje zamienic je w liczby( inty) do pamieci

	;1liczba: 
	parsuj_liczbe1:
		mov bl, 11	; ustawia bl na 11, takiej liczby nie mozna podac na wejsciu - jej brak zmiany oznacza blad
	
		mov si, offset liczba1	;ustaw si na liczbe1 i di na jeden w pamieci...
		mov di, offset jeden
		mov bh, 1			;ustaw bh jako 1 - w razie rownosci ustawienie liczby 
		call parsuj_liczbe	;wywoluje parsowanie liczby, jesli jest rowna ustawionej na di, to bl sie zmieni 
		
		mov si, offset liczba1
		mov di, offset dwa
		mov bh, 2
		call parsuj_liczbe
		
		mov si, offset liczba1
		mov di, offset trzy
		mov bh, 3
		call parsuj_liczbe
		
		mov si, offset liczba1
		mov di, offset cztery
		mov bh, 4
		call parsuj_liczbe
		
		mov si, offset liczba1
		mov di, offset piec
		mov bh, 5
		call parsuj_liczbe
		
		mov si, offset liczba1
		mov di, offset szesc
		mov bh, 6
		call parsuj_liczbe
		
		mov si, offset liczba1
		mov di, offset siedem
		mov bh, 7
		call parsuj_liczbe
		
		mov si, offset liczba1
		mov di, offset osiem
		mov bh, 8
		call parsuj_liczbe
		
		mov si, offset liczba1
		mov di, offset dziewiec
		mov bh, 9
		call parsuj_liczbe
		
		mov si, offset liczba1
		mov di, offset zero
		mov bh, 0
		call parsuj_liczbe
		
		cmp bl, 11 			;po porownaniu z kazdym slowem, jesli zadne sie nie udalo to wypisuje blad i koniec programu
		jz wypisz_blad
		
		mov byte ptr ds:[liczbaint1], bl	;jesli sie udalo to ustawiam liczbaint1 na sparsowana liczbe
		
		 
		
	parsuj_liczbe2:
		mov bl, 11	; ustawia bl na 11, takiej liczby nie mozna podac na wejsciu - jej brak zmiany oznacza blad
	
		mov si, offset liczba2	;ustaw si na liczbe1 i di na jeden w pamieci...
		mov di, offset jeden
		mov bh, 1			;ustaw bh jako 1 - w razie rownosci ustawienie liczby 
		call parsuj_liczbe	;wywoluje parsowanie liczby, jesli jest rowna ustawionej na di, to bl sie zmieni 
		
		mov si, offset liczba2
		mov di, offset dwa
		mov bh, 2
		call parsuj_liczbe
		
		mov si, offset liczba2
		mov di, offset trzy
		mov bh, 3
		call parsuj_liczbe
		
		mov si, offset liczba2
		mov di, offset cztery
		mov bh, 4
		call parsuj_liczbe
		
		mov si, offset liczba2
		mov di, offset piec
		mov bh, 5
		call parsuj_liczbe
		
		mov si, offset liczba2
		mov di, offset szesc
		mov bh, 6
		call parsuj_liczbe
		
		mov si, offset liczba2
		mov di, offset siedem
		mov bh, 7
		call parsuj_liczbe
		
		mov si, offset liczba2
		mov di, offset osiem
		mov bh, 8
		call parsuj_liczbe
		
		mov si, offset liczba2
		mov di, offset dziewiec
		mov bh, 9
		call parsuj_liczbe
		
		mov si, offset liczba2
		mov di, offset zero
		mov bh, 0
		call parsuj_liczbe
		
		cmp bl, 11 			;po porownaniu z kazdym slowem, jesli zadne sie nie udalo to wypisuje blad i koniec programu
		jz wypisz_blad
		
		mov byte ptr ds:[liczbaint2], bl	;jesli sie udalo to ustawiam liczbaint2 na sparsowana liczbe
		
		
	ret
parsuj_liczbe: 
	
	call porownaj_stringi	;porownanie stringow
	

	cmp ah, 1		;sprawdza flage wrzucona na ah- czy stringi byly rowne 
	jz ustaw_bl 		;jesli flaga byla 1 [stringi rowne] to ustaw bl na bh i wyjdz z procedury
	
	jmp koniec_parsuj_liczbe ;jesli flaga byla 0[nierowne stringi] to wyjdz z procedury 
	
	ustaw_bl:
		mov bl,bh
		jmp koniec_parsuj_liczbe
	
	koniec_parsuj_liczbe:
		ret

witaj:	;tutaj program wypisze powitanie:
	mov dx, offset powitanie	; offset powitanie do dx
	call print
	ret

wypisz_blad: ;wypisanie informacji o bledzie
	mov dx, offset bledne_dane ;offset bledne_dane do dx
	call print
	jmp koniec

print: ;wypisanie na ekran tego na co wskazuje dx	
	
	mov ah,9 ;wypisz string z bufora ds:dx
	int 21h 
	ret

wypisanie_wyniku:
	mov dx, offset wypisz_wynik
	call print
	
	ret
	
zczytaj: ;tutaj zczytanie tekstu z klawiatury do pamieci programu:
	mov ax,  seg wejscie
	mov ds, ax			; zczytanie do ds segmentu 'data1'
	
	mov dx,  offset wejscie	; zczytuje offset wejscie do dx
	mov ah, 0Ah ;wywoluje A funkcje przerwania- zczytanie z konsoli do bufora ds:dx
	int 21h
	xor bx, bx ; zeruje bx
	mov bl, byte ptr ds:[wejscie +1]	;ustawiam bl na dlugosc zczytanego wejscia
	mov byte ptr ds:[bx+2], '$' 				;ustawiam ostatni znak wejscia na $
	ret 
	
zparsuj_wejscie: ;tutaj zamienia sie dane na wejsciu w 3 stringi, liczb i operatora
	
	mov si, offset [wejscie+2]		;ustaw znacznik si, na stringa wpisanego przez uzytkownika, od 2 bo wczesniej sa informacje o buforze 
	
		call pomin_spacje		;pomijam spacje w slowie
		mov di, offset liczba1	;ustawiam znacznik kopiowania na liczba1
		call parsuj_slowo		;kopiuje pierwsze slowo do liczba1
		
		call pomin_spacje		
		mov di, offset operator	;ustawiam znacznik kopiwoania na operator
		call parsuj_slowo		;kopiuje drugie slowo do operator
		
		call pomin_spacje
		mov di, offset liczba2	;ustawiam znacznik kopiowania na liczba2
		call parsuj_slowo		;kopiuje trzecie slowo do liczba2
	
	ret

pomin_spacje:;tutaj pomija sie spacje na slowie wskazywanym przez si
	petla_spacje:
		mov bl, byte ptr ds:[si]
		
		cmp bl, ' ' 	;sprawdzdam czy w wejsciu wskazuje aktualnie na spacje, jesli tak, przechodze wskaznikiem dalej
		jz przeskocz_1spacje
		
		cmp bl, 9			;to samo dla znaku tab
		jz przeskocz_1spacje
		
		jmp koniec_spacje; jesli nie to wyskocz z procedury, kontynuuj wykonywanie programu
		
	przeskocz_1spacje:	;przeskakuje znak czyli zwieksza licznik o 1 i wraca do petli
		inc si
		jmp petla_spacje
		
	koniec_spacje:
	ret
	
parsuj_slowo:
	mov cx, 10		;ustawiam licznik na 10, najdluzsze mozliwe slowo na wejsciu jest krotsze
	
	petla_slowo:
		mov bl, byte ptr ds:[si] 	;skopiuj ds:si czyli zrodlowy znak do bl
		
		cmp bl, '$'			;sprawdz czy to nie znak konca slowa/ spacja /koniec lini 
		jz koniec_parse
		cmp bl, ' '
		jz koniec_parse
		cmp bl, 10 
		jz koniec_parse
		cmp bl, 13
		jz koniec_parse
		cmp bl, 9
		jz koniec_parse
		
		mov byte ptr ds:[di], bl		;skopiuj bl do ds:di  
		inc di
		inc si				;zwieksz wskazniki 
		
		dec cx				;zmniejsz licznik, jesli zero to znaczy ze slowo jest zbyt dlugie, blad
		jz wypisz_blad
		
		jmp petla_slowo     ;skocz na poczatek petli, kopiuj nastepny znak
		
	koniec_parse:
	mov byte ptr ds:[di], '$' ; ustaw znak konca slowa na koncu skopiowanego stringa
	ret
	
porownaj_stringi:
	dec di		;zmniejsza di przed petla o 1 aby pierwsze inc di w petli nie sprawilo rozjechania sie znacznikow
	
	comp_char:
	
		lodsb 			;zaladuj ds:si do al
		inc di			;zwieksz indeks 
		cmp byte ptr ds:[di], al	;porownaj znak ds:di z al
		jne rozne_znaki; rozne znaki, skocz do oznaczenia bledu porownania 
		
		cmp al,'$'
		jne comp_char ; nie jest to koniec znakow, sprawdzaj dalej
		
		mov ah, 1 ;jesli to byly konce znakow wrzuc na ah 1- oznaczenie ze stringi byly rowne
		jmp koniec_porown_string ;wyjscie z procedury
		
	rozne_znaki:
		mov ah, 0 ;znaki sa rozne i flaga na ah
		jmp koniec_porown_string;wyjscie z procedury
		
	;wyjscie z procedury
	koniec_porown_string:
		
	ret
	
	
code1 ends

stack1 segment stack		;stos
	dw 250	dup (?)
	w_stos	dw 	?

stack1 ends

end start1
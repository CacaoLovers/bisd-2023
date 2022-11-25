
insert into hw3.desk (number, capacity, place, description) values (1, 4, 'lounge', 'nice place'),
                                                           (2, 5, 'VIP-zone', 'with smoke'),
                                                           (3, 4, 'veranda', 'nice view'),
                                                           (4, 3, 'lounge', 'place for children'),
                                                           (5, 4, 'VIP-zone', 'f4 double apple');

insert into hw3.guest (first_name, last_name, phone_number, comment) values ('Natalya', 'Pehota', '89142546687', 'nice woman'),
                                                                            ('Mihal', 'Palych', '89046457667', 'nice man'),
                                                                            ('Mishael', 'Zubenko', '5654354', 'love a gum'),
                                                                            ('Leg', 'Zebrov', '89074563454', 'important!'),
                                                                            ('Ivan', 'Kojimo', '89564538593', 'low service');

insert into hw3.booking (id_guest, id_table, time, duration, number_person) values (1, 1, '22-02-10 20:00', 4, 4),
                                                                                   (1, 2, '22-02-10 20:00', 4, 4),
                                                                                   (2, 1, '22-07-10 20:00', 3, 6),
                                                                                   (3, 4, '22-02-10 20:00', 3, 4);
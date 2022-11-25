<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Создание объявления</title>
    <link href="/css/missing.css" rel="stylesheet" type="text/css">
    <link href="/css/head.css" rel="stylesheet" type="text/css">
</head>
<body>
    <c:if test="${requestScope.message != null}">
        <div class="error-block">
            <p>${requestScope.message}</p>
        </div>
    </c:if>
    <header>
        <span class = "logo"><img src="../../images/logo.jpg"><a href="/">PETHOME</a></span>
        <span class = header-menu>
                <a class = "header-menu-entity" href="/map?action=found">Потерял</a>
                <a class = "header-menu-entity" href="/map?action=lost">Нашел</a>
                <a class = "header-menu-entity" href="/volunteer">
                        <c:if test="${sessionScope.volunteer == null}">Стать волонтером</c:if>
                        <c:if test="${sessionScope.volunteer != null}">Волонтерство</c:if>
                    </a>
            </span>
        <c:choose >
            <c:when test="${sessionScope.status.equals(\"guest\")}">
                <form class="login-button-form" action="login" method="get">
                    <button type="submit" class="login-button">Войти</button>
                </form>
            </c:when>
            <c:when test="${sessionScope.status.equals(\"login\")}">
                <form class="login-button-form" action="profile" method="get">
                    <input type ="hidden" name="login" value="${sessionScope.login}">
                    <button type = "submit" class="login-button">${sessionScope.login}</button>
                </form>
            </c:when>
        </c:choose>
    </header>
    <main>

        <div class = "scroll-add-missing">
            <div class = "up-step">

                <span class="up-step-entity" <c:if test="${requestScope.addStep.equals(\"description\")}">style = "background-color: #624545"</c:if>></span>
                <span class="up-step-entity" <c:if test="${requestScope.addStep.equals(\"location\")}">style = "background-color: #624545"</c:if>></span>
                <span class="up-step-entity" <c:if test="${requestScope.addStep.equals(\"photo\")}">style = "background-color: #624545"</c:if>></span>
                <span class="up-step-entity" <c:if test="${requestScope.addStep.equals(\"contact\")}">style = "background-color: #624545"</c:if>></span>

            </div>
        </div>
            <c:choose>
                <c:when test="${requestScope.addStep.equals(\"description\")}">
                    <div class = "description-add-missing">
                        <form class = "form-add" action="missing" method="post">
                            <div class = "info-edit-entity">Основная информация</div>
                            <div class = "edit-entity">
                                ${requestScope.foundMessage} <input required type="text" name="name" value="<c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getName()}</c:if>">
                                Был потерен <input required type="date" value="<c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getDate()}</c:if>" name="date">
                            </div>
                            <div class = "edit-entity">
                                Какие приметы <textarea name="description"><c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getDescription()}</c:if></textarea>
                                <input type="hidden" name="addStep" value="description">
                                <input type="hidden" name="side" value="${requestScope.action}">
                            </div>
                            <div class="edit-entity-button">
                                <button type="submit" name="action" value="next">Далее</button>
                            </div>
                        </form>
                    </div>
                </c:when>

                <c:when test="${requestScope.addStep.equals(\"location\")}">
                    <div class = "location-add-missing">
                        <div class="yandex-map-container">
                            <div class="location-add-missing-input">
                                <div class = "info-edit-entity" style = "margin-top: 20px">Расположение</div>
                                <form class = "form-add" action="missing" method="post">
                                    <div class = "edit-entity">
                                        Введите город <input required type = "text" name="city" value="<c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getCity()}</c:if>">
                                    </div>
                                    <div class = "edit-entity">
                                        Введите район <input required type = "text" name="district" value="<c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getDistrict()}</c:if>">
                                    </div>
                                    <div class = "edit-entity">
                                        Введите адрес <textarea required name="address"><c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getStreet()}</c:if></textarea>
                                        <input type="hidden" name="addStep" value = "location">
                                    </div>
                                    <div class="edit-entity-button">
                                        <input type="hidden" name = "pos_x" value="<c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getPosX()}</c:if>">
                                        <input type="hidden" name = "pos_y" value="<c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getPosY()}</c:if>">
                                        <button type="submit" name="action" value = "back">Назад</button>
                                        <button type="submit" name="action" value = "next">Далее</button>
                                    </div>
                                </form>
                            </div>
                            <div class = "yandex-map" id = "map"></div>
                        </div>
                    </div>
                </c:when>

                <c:when test="${requestScope.addStep.equals(\"photo\")}">
                    <div class = "info-edit-entity">Выбор фото</div>
                    <div class = "photo-add-missing">
                        <form class = "form-add" action="addphoto" method="post" enctype="multipart/form-data">
                            <div class="photo-form">
                                <c:if test="${sessionScope.missingForm.getPathImage() == null}">
                                    <input type="file" id = "file" name="file" size="600" accept=".jpg, .jpeg, .png, .gif">
                                </c:if>
                                <c:if test="${sessionScope.missingForm.getPathImage() != null}">
                                    <input type="file"  id = "file"  name="file" size="600" style="background-image: url('images/${sessionScope.missingForm.getPathImage()}')"  accept=".jpg, .jpeg, .png, .gif">
                                </c:if>
                                <button id = "file-submit" type="submit" name="action" value="load_photo">Добавить фото</button>
                            </div>
                        </form>

                        <form action="missing" method="post">
                            <input type="hidden" name="addStep" value = "photo">
                            <div class="edit-entity-button">
                                <button type="submit" name="action" value = "back">Назад</button>
                                <button type="submit" name="action" value = "next">Далее</button>
                            </div>
                        </form>
                    </div>
                </c:when>

                <c:when test="${requestScope.addStep.equals(\"contact\")}">
                <div class = "contact-add-missing">
                    <form class = "form-add" action="missing" method="post">
                        <div class = "info-edit-entity">Как с вами можно связаться</div>
                        <input type="hidden" name="addStep" value = "contact">
                        <div class="edit-entity">
                            Телефон для связи<input type="tel" pattern="[8]{1}[0-9]{10}" name="phone_number" value="<c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getPhoneNumber()}</c:if>">
                        </div>
                        <div class="edit-entity">
                            Почта для связи<input type="email" name="mail" value="<c:if test="${sessionScope.missingForm != null}">${sessionScope.missingForm.getMail()}</c:if>">
                        </div>
                        <div class="edit-entity-button" style="width: 500px">
                            <button type="submit" name="action" value = "back">Назад</button>
                            <button type="submit" name="action" value = "create">Создать объявление</button>
                        </div>
                    </form>
                </div>
                </c:when>
            </c:choose>


        </div>

    </main>
    <script src="/js/jquery-3.6.1.min.js"></script>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&amp;apikey=fad4af17-6974-40f6-9835-1481bab2a7ad" type="text/javascript"></script>
    <script type="text/javascript">

        ymaps.ready(init);

        function init() {

            var userPlacemark,
                yandexMap = new ymaps.Map("map", {
                    center: [55.801390, 49.031338],
                    zoom: 12,
                    controls: ['zoomControl', 'typeSelector']
                }, {
                    searchControlProvider: 'yandex#search'
                });

            yandexMap.events.add('click', function (e) {
                var coords = e.get('coords');

                if (userPlacemark) {
                    userPlacemark.geometry.setCoordinates(coords);
                }
                else {
                    userPlacemark = createPlacemark(coords);
                    yandexMap.geoObjects.add(userPlacemark);
                    userPlacemark.events.add('dragend', function () {
                        getAddress(userPlacemark.geometry.getCoordinates());
                    });
                }
                getAddress(coords);

                ymaps.geocode(coords).then(function (res) {
                    var firstGeoObject = res.geoObjects.get(0);

                    if(firstGeoObject.getAddressLine().split(', ')[3].endsWith("район")){
                        $('input[name="district"]').val(firstGeoObject.getAddressLine().split(', ')[3]);
                    } else {
                        $('input[name="district"]').val(firstGeoObject.getAdministrativeAreas()[1]);
                    }

                    $('input[name="city"]').val(firstGeoObject.getLocalities()[0]);

                    const addressList = firstGeoObject.getAddressLine().split(', ');
                    address = "";

                    for (i = 2; i < addressList.length; i++){
                        address += addressList[i] + ', ';
                    }

                    address = address.slice(0, address.length - 2);

                    $('textarea[name="address"]').val(address);
                });

                $('input[name="pos_x"]').val(userPlacemark.geometry.getCoordinates()[0]);
                $('input[name="pos_y"]').val(userPlacemark.geometry.getCoordinates()[1]);
            });

            function createPlacemark(coords) {
                return new ymaps.Placemark(coords, {
                    iconCaption: 'поиск...'
                }, {
                    preset: 'islands#violetDotIconWithCaption',
                    draggable: true
                });
            }


            function getAddress(coords) {
                userPlacemark.properties.set('iconCaption', 'поиск...');
                ymaps.geocode(coords).then(function (res) {
                    var firstGeoObject = res.geoObjects.get(0);

                    userPlacemark.properties
                        .set({
                            iconCaption: [
                                firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas(),
                                firstGeoObject.getThoroughfare() || firstGeoObject.getPremise()
                            ].filter(Boolean).join(', '),
                            balloonContent: firstGeoObject.getAddressLine()
                        });
                });
            }
        }
        function submit () {
            document.getElementById('file-submit').click();
        }
        document.getElementById('file').addEventListener('change', submit, false);



    </script>
    <script>
        <c:if test="${requestScope.message != null}">
        $(document).ready(function () {
            $('.error-block').animate({top: "20px"}, 800, "linear", function (){$('.error-block').fadeOut(4000)})
        })
        </c:if>
    </script>
</body>
</html>

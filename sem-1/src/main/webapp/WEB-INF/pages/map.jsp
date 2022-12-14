
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Карта</title>
    <link href="/css/head.css" rel="stylesheet" type="text/css">
    <link href="/css/map.css" rel="stylesheet" type="text/css">
</head>
<body>
    <c:if test="${requestScope.message != null}">
        <div class="error-block">
            <p>${requestScope.message}</p>
        </div>
    </c:if>
    <div id = "pelena" class = "pelena" onclick="hideInfoBlock()"></div>
    <div id  = "entity-block" class = "entity-block">
        <div class = "entity-block-information-header">
            <p id = "entity-block-information-header"></p>
            <div class = "entity-block-close" id = "entity-block-close" onclick="hideInfoBlock()"></div>
        </div>
        <div class = "entity-block-photo-holder">
            <div class="entity-block-photo" id = "entity-block-photo"></div>
        </div>
        <div class = "entity-block-information-holder">
            <div class = "entity-block-information">
                <p id = "entity-block-information-description"></p>
                <p id = "entity-block-information-city"></p>
                <p id = "entity-block-information-district"></p>
                <p id = "entity-block-information-street"></p>
                <p id = "entity-block-information-status"></p>
                <p id = "entity-block-information-date"></p>
                <p id = "entity-block-information-owner"></p>
            </div>
        </div>
        <div class="entity-block-information-button-holder">
            <div class="entity-block-information-button-container">
                <a href="" class="entity-block-information-button" id = "entity-block-information-button">Профиль</a>
                <c:if test="${requestScope.action != null && requestScope.action.equals(\"lost\")}">
                    <form style="display: inline-block" action="/map"  method="post">
                        <input type="hidden" name="missing_id" id="entity-missing-id">
                        <button type="submit" class="entity-block-information-button" name="action" value="found">Найден</button>
                    </form>
                </c:if>
                <c:if test="${sessionScope.role.equals('admin')}">
                    <form style="display: inline-block" action="/map" method="post">
                        <input type="hidden" name="missing_id" id="entity-missing-id-remove">
                        <button type="submit" class="entity-block-information-button" name="action" value="remove">Удалить</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>

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
        <div class = "yandex-map" id = "map">
            <div class = "search-block">
                    <form class="create-add-block" action="/missing" method="get">
                        <c:if test="${requestScope.action.equals(\"lost\")}">
                        <button type="submit" name="action" value="found" class="create-add">Создать объявление</button>
                        </c:if>
                        <c:if test="${requestScope.action.equals(\"found\")}">
                            <button type="submit" name="action" value="lost" class="create-add">Создать объявление</button>
                        </c:if>
                    </form>

                    <c:forEach items="${missingSet}" var="missing">
                        <form class = "search-form">
                            <button type="button" class = "missing-container" id="missing-container-${missing.getId()}" onclick="showInfoBlock([['name','${requestScope.foundMessage} ${missing.getName()}']
                                    , ['description', 'Приметы: ${missing.getDescription()}']
                                    , ['street', 'Локация: ${missing.getStreet()}']
                                    , ['date', '${requestScope.foundDate} ${missing.getDate()}']
                                    , ['pathImage', '${missing.getPathImage()}']
                                    , ['district', 'Район: ${missing.getDistrict()}']
                                    , ['status', 'Cтатус: активный']
                                    , ['owner', '${missing.getLoginOwner()}']
                                    , ['posX', '${missing.getPosX()}']
                                    , ['posY', '${missing.getPosY()}']
                                    , ['id', '${missing.getId()}']
                                    , ['city', 'Город ${missing.getCity()}']]);">
                                <div class = "search-photo" style="background-image: url('images/${missing.getPathImage()}')"></div>
                                <div class = "missing-information-block">
                                    <p class = "missing-information-entity">${requestScope.foundMessage} ${missing.getName()}</p>
                                    <p class = "missing-information-entity" title= "${missing.getDescription()}">Приметы: ${missing.getDescription()}</p>
                                    <p class = "missing-information-entity" title="${missing.getStreet()}">${missing.getStreet()}</p>
                                    <p class = "missing-information-entity">${requestScope.foundDate} ${missing.getDate()}</p>
                                </div>
                            </button>
                        </form>
                    </c:forEach>
            </div>
        </div>
    </main>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&amp;apikey=fad4af17-6974-40f6-9835-1481bab2a7ad" type="text/javascript"></script>
    <script src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript">
        ymaps.ready(init);
        $('#pelena').hide();
        $('#entity-block').hide();
        let yandexMap;

        function showInfoBlock(args){
            const mapInfo = new Map(args);
            $('#pelena').show();
            $('#entity-block').show();
            $('#pelena').css(
                'display', 'block'
            );
            $('#entity-block').css(
                'display', 'block'
            );

            document.getElementById('entity-block-information-header').innerHTML = mapInfo.get('name');
            document.getElementById('entity-block-information-description').innerHTML = mapInfo.get('description');
            document.getElementById('entity-block-information-street').innerHTML = mapInfo.get('street');
            document.getElementById('entity-block-information-date').innerHTML = mapInfo.get('date');
            document.getElementById('entity-block-information-status').innerHTML = mapInfo.get('status');
            document.getElementById('entity-block-information-owner').innerHTML = 'Объявление разместил: <a href=\'/profile?login=' + mapInfo.get('owner') + '\'>' + mapInfo.get('owner') + '</a>';
            document.getElementById('entity-block-information-city').innerHTML = mapInfo.get('city');
            document.getElementById('entity-block-information-district').innerHTML = mapInfo.get('district');
            document.getElementById('entity-block-information-button').setAttribute('href', '/profile?login=' + mapInfo.get('owner'));
            <c:if test="${requestScope.action != null && requestScope.action.equals(\"lost\")}">
                document.getElementById('entity-missing-id').value = mapInfo.get('id');
            </c:if>
            <c:if test="${sessionScope.role.equals('admin')}">
                document.getElementById('entity-missing-id-remove').value = mapInfo.get('id');
            </c:if>
            $('#entity-block-photo').css(
                'background-image', 'url(\'images/' + mapInfo.get('pathImage') + '\')'
            );

            yandexMap.setCenter([mapInfo.get('posX'), mapInfo.get('posY')]);

        }

        function hideInfoBlock(){
            $('#pelena').hide();
            $('#entity-block').hide();
        }

        function init() {

                yandexMap = new ymaps.Map("map", {
                    center: [55.796515, 49.106936],
                    zoom: 12,
                    controls: ['zoomControl', 'typeSelector']
                }, {
                    searchControlProvider: 'yandex#search'
                });

            <c:forEach items="${missingSet}" var="missing">
                placeShelter${missing.getId()} = new ymaps.Placemark([${missing.getPosX()}, ${missing.getPosY()}], {

                    iconContent: '${missing.getName()}',
                    balloonContentHeader: '${requestScope.foundMessage} ${missing.getName()}',
                    balloonContentBody: '<div class = \"search-photo\" style=\"background-image: url(\'images/${missing.getPathImage()}\')\"></div>' +
                                '<div class = "missing-information-block">' +
                                    '<p class = "missing-information-entity">${requestScope.foundMessage} ${missing.getName()}</p>' +
                                    '<p class = "missing-information-entity" title = "${missing.getDescription()}">Приметы: ${missing.getDescription()}</p>' +
                                    '<p class = "missing-information-entity" title = "${missing.getStreet()}">${missing.getStreet()}</p>' +
                                    '<p class = "missing-information-entity">${requestScope.foundDate} ${missing.getDate()}</p>' +
                                '</div>'

                }, {

                    preset: 'islands#brownCircleDotIcon'

                })

                yandexMap.geoObjects.add(placeShelter${missing.getId()});


                placeShelter${missing.getId()}.events.add('click', function (e) {
                    if (!placeShelter${missing.getId()}.balloon.isOpen()) {
                        var coords = e.get('coords');
                        placeShelter${missing.getId()}.balloon.open(coords)
                    }
                    else {
                        placeShelter${missing.getId()}.balloon.open(coords);
                        placeShelter${missing.getId()}.balloon.close();
                    }
                });


            </c:forEach>
        }

        <c:if test="${requestScope.missingId != null}" >

            $('#missing-container-${requestScope.missingId}').click();

        </c:if>

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

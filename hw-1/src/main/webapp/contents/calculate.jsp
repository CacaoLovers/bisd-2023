<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 07.09.2022
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Калькулятор</title>
    <meta charset="UTF-8">
    <link href="../css/calculator.css" rel="stylesheet" type="text/css">
</head>
<body>

<main>
    <div class="header">
        <div id = "output-console"></div>
        <span id="output-block">0</span>
    </div>
    <table class="iksweb">
        <tbody>
        <tr>
            <td><button class = "operation-button" onclick="unaryOperation('%')">%</button></td>
            <td><button class = "operation-button" onclick="clearOperation()">CE</button></td>
            <td><button class = "operation-button" onclick="clearOperation()">C</button></td>
            <td><button class = "operation-button" onclick="unaryOperation('-')">-</button></td>
        </tr>
        <tr>
            <td><button class = "operation-button" onclick="unaryOperation('1/x')">1/x</button></td>
            <td><button class = "operation-button" onclick="unaryOperation('x**2')">x**2</button></td>
            <td><button class = "operation-button" onclick="unaryOperation('-/x')">-/x</button></td>
            <td><button class = "operation-button" onclick="binaryOperation('/')">/</button></td>
        </tr>
        <tr>
            <td><button class = "numberButton" onclick = "clickButtonNumber(7)">7</button></td>
            <td><button class = "numberButton" onclick = "clickButtonNumber(8)">8</button></td>
            <td><button class = "numberButton" onclick = "clickButtonNumber(9)">9</button></td>
            <td><button class = "operation-button" onclick="binaryOperation('*')">*</button></td>
        </tr>
        <tr>
            <td><button class = "numberButton" onclick = "clickButtonNumber(4)">4</button></td>
            <td><button class = "numberButton" onclick = "clickButtonNumber(5)">5</button></td>
            <td><button class = "numberButton" onclick = "clickButtonNumber(6)">6</button></td>
            <td><button class = "operation-button" onclick="binaryOperation('-')">-</button></td>
        </tr>
        <tr>
            <td><button class = "numberButton" onclick = "clickButtonNumber(1)">1</button></td>
            <td><button class = "numberButton" onclick = "clickButtonNumber(2)">2</button></td>
            <td><button class = "numberButton" onclick = "clickButtonNumber(3)">3</button></td>
            <td><button class = "operation-button" onclick="binaryOperation('+')">+</button></td>
        </tr>
        <tr>
            <td><button class = "operation-button">+/-</button></td>
            <td><button class = "numberButton" onclick = "clickButtonNumber(0)">0</button></td>
            <td><button class = "operation-button" onclick="addPoint()">.</button></td>
            <td><button class = "operation-button" onclick="equalsOperation()">=</button></td>
        </tr>
        </tbody>
    </table>
</main>
</body>
<script>
    clearOperation();
    let mathConvert = {
        '+': function (x, y) { return x + y },
        '-': function (x, y) { return x - y },
        '*': function (x, y) { return x * y},
        '/': function (x, y) { return x / y}
    };

    let unaryConvert = {
        '1/x': function (x) { return 1 / x },
        'x**2': function (x) { return x**2 },
        '-': function (x) { return -x },
        '-/x': function (x) { return Math.sqrt(x) },
    }


    function clickButtonNumber(a) {
        var output = document.getElementById("output-block");
        var outputConsole = document.getElementById("output-console");
        if (output.innerHTML == 0){
            output.innerHTML = a;
            outputConsole.innerHTML += a;
        } else {
            if (valueOne != 0 && valueTwo == 0) output.innerHTML = "";
            output.innerHTML += a;
            valueTwo += a;
            outputConsole.innerHTML += a;
        }
    }

    function binaryOperation(operator){
        var output = document.getElementById("output-block");
        var outputConsole = document.getElementById("output-console");
        if (output.innerHTML == 0){
            console.log("Ошибка операции")
            return;
        }

        if (valueOne == 0){
            valueOne = output.innerHTML;
            valueTwo = "";
            this.operator = operator;
            outputConsole.innerHTML += " " + operator + " ";
            return;
        }
        if (valueTwo != ""){
            valueOne = mathConvert[this.operator](Number(valueOne), Number(output.innerHTML))
            output.innerHTML = valueOne;
            outputConsole.innerHTML += " " + operator + " ";
            valueTwo = "";
        } else {
            outputConsole.innerHTML = "(" + outputConsole.innerHTML + ")";
            outputConsole.innerHTML += " " + operator + " ";
        }
        this.operator = operator;
    }

    function unaryOperation(operator){
        var output = document.getElementById("output-block");
        output.innerHTML = unaryConvert[operator](output.innerHTML);
    }

    function equalsOperation(){
        var output = document.getElementById("output-block");
        valueOne = mathConvert[operator](Number(valueOne), Number(valueTwo));
        output.innerHTML = valueOne;
        valueTwo = "";
    }

    function clearOperation(){
        valueOne = "";
        valueTwo = "";
        operator = 0;
        document.getElementById("output-block").innerHTML = 0;
        document.getElementById("output-console").innerHTML = "";
        console.log("Clear success")
    }

    function addPoint(){
        document.getElementById("output-block").innerHTML += ".";
        document.getElementById("output-console").innerHTML += ".";
    }
</script>
</html>


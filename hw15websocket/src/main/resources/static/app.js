var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/admin/users', function (data) {
            // showGreeting(JSON.parse(greeting.body).content);
            showGreeting(JSON.parse(data.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/admin/users", {}, JSON.stringify({'name': $("#name").val(), 'age': $("#age").val()}));
}

function showGreeting(message) {
    console.log(message.content)
    $("#users").append("<tr>" +
        "<td>" + message.id + "</td>" +
        "<td>" + message.name + "</td>" +
        "<td>" + message.age + "</td>" +
        "</tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#btnSave" ).click(function() { sendName(); });
});
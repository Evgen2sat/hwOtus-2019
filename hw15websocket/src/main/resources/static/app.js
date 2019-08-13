var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/admin/users', function (data) {
            showUsers(JSON.parse(data.body));
        });
    });
}

function sendName() {
    stompClient.send("/app/admin/users", {}, JSON.stringify({'name': $("#name").val(), 'age': $("#age").val()}));
}

function showUsers(message) {
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
    $( "#btnSave" ).click(function() { sendName(); });
});
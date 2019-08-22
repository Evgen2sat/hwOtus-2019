var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/admin/users', function (data) {
            showUsers(JSON.parse(data.body));
        });
        getAllUsers.call(this);
    });
}

function sendName() {
    stompClient.send("/app/admin/users", {}, JSON.stringify({'name': $("#name").val(), 'age': $("#age").val()}));
}

function getAllUsers() {
    stompClient.send("/app/admin/all-users");
}

function showUsers(message) {
    $("#users").empty();
    for(let i = 0; i < message.length; i++) {
        $("#users").append("<tr>" +
            "<td>" + message[i].id + "</td>" +
            "<td>" + message[i].name + "</td>" +
            "<td>" + message[i].age + "</td>" +
            "</tr>");
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#btnSave" ).click(function() { sendName(); });
});
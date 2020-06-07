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
    $("#liveTrades").html("");
}

function connect() {
    var socket = new SockJS('/fall-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/marketTrade', function (tradeSendOut) {
            console.log("json body: " + JSON.parse(tradeSendOut.body).content);
            showTrade(JSON.parse(tradeSendOut.body).content);
            //showTrade(tradeSendOut);

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

function showTrade(message) {
    $("#liveTrades").append("<tr><td>" + message + "</td></tr>");
}

function sendName() {
    stompClient.send("/app/marketTrans", {}, JSON.stringify({'name': $("#name").val()}));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $("#connect").click(function() { 
        console.log("connecting?");
        connect(); });
    $("#disconnect").click(function() { disconnect(); });  
    $("#send").click(function() { sendName(); });
});
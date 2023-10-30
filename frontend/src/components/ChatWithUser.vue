<template>
    <h3> {{receiver}} 님과의 채팅 </h3>
    <button @click="goToMyRoom"> 목록으로 돌아가기 </button>
    <div>
        <ul>
            <li v-for="message in messages" :key="message.id">
                {{ message.sender }}: {{ message.content }}
            </li>
        </ul>
    </div>
    <div>
        <input v-model="messageInput" @keyup.enter="sendMessage" placeholder="메시지를 입력하세요">
        <button @click="sendMessage">전송</button>
    </div>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import axios from 'axios';

export default {
    data() {
        return {
            roomId: '',
            receiver: '',
            socket: null,
            messages: [],
            messageInput: "",
            token: ''
        };
    },
    created() {
        this.roomId = localStorage.getItem('roomId');
        this.receiver = localStorage.getItem('receiver');
        this.token = localStorage.getItem('accessToken');
        this.initializeWebSocket();
    },
    methods: {
        async initializeWebSocket() {
            var headers = {
                'Authorization': 'Bearer ' + this.token
            };

            var _this = this
            var socket = new SockJS("/ws");
            _this.stompClient = Stomp.over(socket);

            _this.stompClient.connect(headers, function(frame) {
                console.log('WebSocket 연결 성공'); // 웹소켓 연결이 성공한 경우 콘솔에 메시지 출력
                _this.stompClient.subscribe(`/sub/chat/room/${_this.roomId}`, function(message) {
                    var recv = JSON.parse(message.body);
                    _this.recvMessage(recv);
                });
                _this.sendMessage('ENTER');
            }, function(error) {
                console.error('WebSocket 연결 실패:', error); // 웹소켓 연결이 실패한 경우 콘솔에 에러 메시지 출력
            });
        },
        sendMessage(type) {
            this.stompClient.send(
                '/pub/chat/message',
                { token: this.token },
                JSON.stringify({ type: type, roomId: this.roomId, message: this.message })
            );
            this.message = '';
        },
        recvMessage(recv) {
            this.messages.unshift({ type: recv.type, sender: recv.sender, message: recv.message });
        },
        goToMyRoom() {
            this.$router.push({name: 'MyChatRoom'});
        }
    }
};
</script>

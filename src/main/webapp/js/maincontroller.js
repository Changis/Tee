/**
 * Created by David Chang on 2016-10-27.
 */
var app = angular.module('mainApp', []);
app.controller('mainController', function($scope, $http, $interval) {

    $scope.dbReply = "";
//    $scope.pname = "glader";
    $scope.restURL = "http://localhost:8080/Tee-1.0-SNAPSHOT/api/";
    // $scope.searchedId = 0;
    // $scope.idToUpdate = "";
    // $scope.newName = "";
    // $scope.idToDelete=-1;
    $http.get($scope.restURL + "alltasks").then(function(response){
        $scope.taskList = response.data;
    });

    $scope.addPerson = function(){
        $http.get($scope.restURL + "add/"+$scope.pname).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.addResult = response.data;
        });
    };

    $scope.addTeam = function(){
        $http.get($scope.restURL + "addteam/"
            + $scope.tname + "/" + $scope.shorttname).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.addTeamResult = response.data;
        });
    };

    $scope.getNameById = function(){

        $http.get($scope.restURL + "get/"+$scope.searchedId).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.searchResult = response.data;
        });
    };

    $scope.getAllNames = function () {
        $http.get($scope.restURL + "get/all").then(function(response){
            // $http.get("/api/get/all").then(function(response){
            $scope.dbReply = response.statusText;
            $scope.personList = response.data;
        });
    };

    $scope.getAllTeams = function () {
        $http.get($scope.restURL + "get/allteams").then(function(response){
            // $http.get("/api/get/all").then(function(response){
            $scope.dbReply = response.statusText;
            $scope.teamList = response.data;
        });
    };

    $scope.getAllTasks = function(){
        $http.get($scope.restURL + "alltasks").then(function(response){
            $scope.dbReply = response.statusText;
            $scope.taskList = response.data
        });
    }

    $scope.getMembersList = function () {
        $http.get($scope.restURL + "getmembers/" + $scope.membersListById).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.getMembersListResult = response.data;
        });
    };

    $scope.getMembersByTask = function () {
        $http.get($scope.restURL + "taskmembers/" + $scope.membersListByTaskId).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.taskMembersResult = response.data;
        });
    };

    $scope.updateNameById = function () {

        $http.get($scope.restURL + "update/"
            + $scope.idToUpdate + '/' + $scope.newName).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.updateResult = response.data;
        });
    };

    $scope.updateTeamByPersonId = function () {
        $http.get($scope.restURL + "updateteam/"
            + $scope.idToUpdate2 + '/' + $scope.newTeamId).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.updateTeamResult = response.data;
        });
    };


    $scope.deleteById = function(){
        $http.get($scope.restURL + "delete/"
            + $scope.idToDelete).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.deleteResult = response.data;
        });
    };

    $scope.deleteTeamById = function(){
        $http.get($scope.restURL + "deleteteam/"
            + $scope.teamIdToDelete).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.deleteTeamResult = response.data;
        });
    };

    $scope.toMessageQueue = function(){
        $http({
            method: "POST",
            url: "http://localhost:8080/Tee-1.0-SNAPSHOT/api/queuemsg/",
            data: { "messageForJMS": $scope.jmsMsg },
            headers: {"Content-Type": "application/json"}
            //headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.jmsMsgResult = response.data;
        });
    };

    $scope.toMessageTopic = function(){
        $http({
            method: "POST",
            url: "http://localhost:8080/Tee-1.0-SNAPSHOT/api/topicmsg/",
            data: { "messageForJMS": $scope.jmsMsg },
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.jmsMsgResult = response.data;
        });
    };

    $scope.createTask = function(){
        $http({
            method: "PUT",
            url: "http://localhost:8080/Tee-1.0-SNAPSHOT/api/task",
            data: { "taskDescription": $scope.newTaskInput },
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.newTaskResult = response.data;
        });
    };

    $scope.assignTask = function(){
        $http({
            method: "POST",
            url: "http://localhost:8080/Tee-1.0-SNAPSHOT/api/assigntask",
            data: {"personId": $scope.personId, "taskId": $scope.taskToAssign.taskId} ,
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.assignTaskResult = response.data;
        });
    };

    $scope.deleteTask = function(){
        $http({
            method: "DELETE",
            url: "http://localhost:8080/Tee-1.0-SNAPSHOT/api/task",
            data: {"id": $scope.taskIdToDelete},
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.deleteTaskResult = response.data;
        });
    };

    $scope.orderPersonsTable = function(x){
        $scope.reverse = ($scope.orderPersonsBy === x) ? !$scope.reverse : false;
        $scope.orderPersonsBy = x;
    }

    $scope.orderTeamMembersTable = function(x){
        $scope.reverseMembers = ($scope.orderTeamMembersBy === x) ? !$scope.reverseMembers : false;
        $scope.orderTeamMembersBy = x;
    }

    $scope.halp = function(){
        alert("halp.");
    };

    $scope.theTime = new Date().toString();
    $interval(function(){
        $scope.theTime = new Date().toString();
    }, 1000);


});
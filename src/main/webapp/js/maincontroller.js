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
    $http.get($scope.restURL + "tasks").then(function(response){
        $scope.taskList = response.data;
    });

    $scope.showPersons = false;
    $scope.showTeams = false;
    $scope.showTasks = false;
    $scope.showMembersList = false;
    $scope.showTaskMembers = false;
    $scope.showPersonsToggle = function(){
        $scope.showPersons = true;
    }

    $scope.addPerson = function(){
//        $http.get($scope.restURL + "add/"+$scope.pname)
        $http({
            method: "PUT",
            url: $scope.restURL + "persons",
            data: {"name": $scope.pname}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.addResult = response.data;
        });
    };

    $scope.addTeam = function(){
//        $http.get($scope.restURL + "addteam/" + $scope.tname + "/" + $scope.shorttname).
        $http({
            method: "PUT",
            url: $scope.restURL + "teams",
            data: {"name": $scope.tname, "shortName": $scope.shorttname}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.addTeamResult = response.data;
        });
    };

    $scope.getPersonById = function(){

        $http.get($scope.restURL + "persons/"+$scope.searchedId).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.searchResult = response.data;
        });
    };

    $scope.getAllPersons = function () {
        $http.get($scope.restURL + "persons").then(function(response){
            // $http.get("/api/get/all").then(function(response){
            $scope.dbReply = response.statusText;
            $scope.personList = response.data;
            $scope.showPersons = ($scope.personList.length > 0) ? true : false;
        });
    };

    $scope.getAllTeams = function () {
        $http.get($scope.restURL + "teams").then(function(response){
            // $http.get("/api/get/all").then(function(response){
            $scope.dbReply = response.statusText;
            $scope.teamList = response.data;
            $scope.showTeams = $scope.teamList.length > 0 ? true : false;
        });
    };

    $scope.getAllTasks = function(){
        $http.get($scope.restURL + "tasks").then(function(response){
            $scope.dbReply = response.statusText;
            $scope.taskList = response.data;
            $scope.showTasks = response.data.length > 0 ? true : false;
        });
    };

    $scope.getTeamMembers = function () {
        $http.get($scope.restURL + "teammembers/" + $scope.membersListById).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.getMembersListResult = response.data;
            $scope.showMembersList = $scope.getMembersListResult.length > 0 ? true : false;
        });
    };

    $scope.getMembersByTask = function () {
        $http.get($scope.restURL + "taskmembers/" + $scope.membersListByTaskId).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.taskMembersResult = response.data;
            $scope.showTaskMembers = $scope.taskMembersResult.length > 0 ? true : false;
        });
    };

    $scope.updatePersonNameById = function () {
        $http({
            method: "POST",
            url: $scope.restURL + "personnameupdate",
            data: {"id": $scope.idToUpdate, "name": $scope.newName}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.updateResult = response.data;
        });
    };

    $scope.updatePersonsTeam = function () {
        $http({
            method: "POST",
            url: $scope.restURL + "personteamupdate",
            data: {"personId": $scope.idToUpdate2, "teamId": $scope.newTeamId}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.updateTeamResult = response.data;
        });
    };

    $scope.deletePersonById = function(){
        $http({
            method: "DELETE",
            url: $scope.restURL + "persons",
            data: {"id": $scope.idToDelete},
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.deleteResult = response.data;
        });
    };

    $scope.deleteTeamById = function(){
        $http({
            method: "DELETE",
            url: $scope.restURL + "teams",
            data: {"id": $scope.teamIdToDelete},
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.deleteTeamResult = response.data;
        });
    };

    $scope.deleteTask = function(){
        $http({
            method: "DELETE",
            url: "http://localhost:8080/Tee-1.0-SNAPSHOT/api/tasks",
            data: {"id": $scope.taskIdToDelete},
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.deleteTaskResult = response.data;
        });
    };

    $scope.createTask = function(){
        $http({
            method: "PUT",
            url: "http://localhost:8080/Tee-1.0-SNAPSHOT/api/tasks",
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
            url: "http://localhost:8080/Tee-1.0-SNAPSHOT/api/taskassignment",
            data: {"personId": $scope.personId, "taskId": $scope.taskToAssign.taskId},
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.assignTaskResult = response.data;
        });
    };

    $scope.unassignTask = function(){
        $http({
            method: "POST",
            url: $scope.restURL + "taskunassignment",
            data: {"personId": $scope.personId, "taskId": $scope.taskToUnassign.taskId},
            headers: {"Content-Type": "application/json"}
        }).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.unassignTaskResult = response.data;
        });
    }

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
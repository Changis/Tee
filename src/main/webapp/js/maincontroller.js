/**
 * Created by David Chang on 2016-10-27.
 */
var app = angular.module('mainApp', []);
app.controller('mainController', function($scope, $http, $interval) {

    $scope.dbReply = "";
    $scope.pname = "glader";
    // $scope.searchedId = 0;
    // $scope.idToUpdate = "";
    // $scope.newName = "";
    // $scope.idToDelete=-1;

    $scope.addPerson = function(){
        $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/add/"+$scope.pname).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.addResult = response.data;
        });
    };

    $scope.addTeam = function(){
        $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/addteam/"
            + $scope.tname + "/" + $scope.shorttname).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.addTeamResult = response.data;
        });
    };

    $scope.getNameById = function(){

        $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/get/"+$scope.searchedId).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.searchResult = response.data;
        });
    };

    $scope.getAllNames = function () {
        $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/get/all").then(function(response){
            // $http.get("/api/get/all").then(function(response){
            $scope.dbReply = response.statusText;
            $scope.personList = response.data;

        });
    };

    $scope.getAllTeams = function () {
        $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/get/allteams").then(function(response){
            // $http.get("/api/get/all").then(function(response){
            $scope.dbReply = response.statusText;
            $scope.teamList = response.data;

        });
    };

    $scope.updateNameById = function () {

        $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/update/"
            + $scope.idToUpdate + '/' + $scope.newName).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.updateResult = response.data;
        });
    };

    $scope.updateTeamByPersonId = function () {

            $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/updateteam/"
                + $scope.idToUpdate2 + '/' + $scope.newTeamId).then(function(response){
                $scope.dbReply = response.statusText;
                $scope.updateTeamResult = response.data;
            });
        };


    $scope.deleteById = function(){

        $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/delete/"
            + $scope.idToDelete).then(function(response){
            $scope.dbReply = response.statusText;
            $scope.deleteResult = response.data;
        });
    }

    $scope.deleteTeamById = function(){

            $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/deleteteam/"
                + $scope.teamIdToDelete).then(function(response){
                $scope.dbReply = response.statusText;
                $scope.deleteTeamResult = response.data;
            });
        }

    $scope.halp = function(){
        alert("halp.");
    };

    $scope.theTime = new Date().toString();
    $interval(function(){
        $scope.theTime = new Date().toString();
    }, 1000);


});
/**
 * Created by David Chang on 2016-10-24.
 */
app.controller('myCtrl', function($scope, $http, $timeout) {
    $scope.firstName = "John";
    $scope.lastName = "Doe";
    $scope.fullName = function() {
        return $scope.firstName + " " + $scope.lastName;
    };
    $scope.even=[2,4,6,8,10];
    $scope.odd=[1,3,5,7,9];
    $scope.vehicles=['bil', 'cykel', 'båt', 'flygplan', 'tåg'];
    $scope.persons=[
        {name:'Jani',country:'Norway'},
        {name:'Hege',country:'Sweden'},
        {name:'Kai',country:'Denmark'}];
    $scope.changeName = function(){
        $scope.firstName = "Jan";
    };
    $http.get("http://localhost:8080/Tee-1.0-SNAPSHOT/api/get/all").then(function(response){
        $scope.returnMsg = response.data;
    });
    $scope.myCountDown = 4;
    $timeout(function(){
        $scope.myCountDown = 3;
    }, 3000);
    $timeout(function(){
        $scope.myCountDown = 2;
    }, 4000);
    $timeout(function(){
        $scope.myCountDown = 1;
    }, 5000);
    $timeout(function(){
        $scope.myCountDown = "?!";
    }, 6000);
});
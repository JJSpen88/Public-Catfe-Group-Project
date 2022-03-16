    var rating = 1

    function highlightCurrStar(star){
        var currStar = parseInt(star.replace("star", ""))
        if(rating > currStar){
            for(let i = 0; i < 5; i++){
                document.getElementById("star"+i.toString()).className = "fa fa-star"
            }
        }
        for(let i = 0; i <= currStar; i++){
            document.getElementById("star"+i.toString()).className += " checked"
        }
        setStarsAfterClicked()
    }

    function unhighlightCurrStar(star){
        for(let i = 0; i < 5; i++){
            document.getElementById("star"+i.toString()).className = "fa fa-star"
        }
        setStarsAfterClicked()
    }
    function selectRating(star){
        var currStar = parseInt(star.replace("star", ""))+1
        document.getElementById("rating").value = currStar
        rating = currStar
        setStarsAfterClicked()
    }

    function setStarsAfterClicked(){
        for(let i = 0; i < rating; i++){
            document.getElementById("star"+i.toString()).className += " checked"
        }
    }
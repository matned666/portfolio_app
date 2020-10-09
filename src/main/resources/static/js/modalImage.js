let img;
let modal;
let modalImg;
let captionText;
function modalImage(elementClicked) {
    img = elementClicked;
    modalImg = document.getElementById("img01");
    captionText = document.getElementById("caption");
    modal = document.getElementById("myModal");
    modal.style.display = "block";
    modalImg.src = img;
    captionText.innerHTML = this.alt;
}

let span = document.getElementsByClassName("close")[0];

span.onclick = function () {
    modal.style.display = "none";
}
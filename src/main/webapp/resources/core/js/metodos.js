function openModal(videoUrl) {
    var modal = document.getElementById("videoModal");
    var modalVideo = document.getElementById("modal-video");
    var thumbnailVideo = document.getElementById("thumbnail-video");

    // Pausar el video de la miniatura
    var src = thumbnailVideo.src;
    thumbnailVideo.src = src;

    modal.style.display = "block";
    modalVideo.src = videoUrl;
}

function closeModal() {
    var modal = document.getElementById("videoModal");
    var modalVideo = document.getElementById("modal-video");
    modal.style.display = "none";
    modalVideo.src = "";
}
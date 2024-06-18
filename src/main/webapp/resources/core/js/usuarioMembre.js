function mostrarPopup() {
    var popup = document.getElementById('popup');
    popup.style.display = 'flex'; /* Mostrar el popup */
}

function cerrarPopup() {
    var popup = document.getElementById('popup');
    popup.style.display = 'none'; /* Ocultar el popup */
}

// Detectar clic en el enlace del paso 3 para mostrar el popup
document.querySelector('.paso:nth-child(3) a').addEventListener('click', function(event) {
    event.preventDefault(); // Prevenir comportamiento por defecto del enlace
    mostrarPopup();
});

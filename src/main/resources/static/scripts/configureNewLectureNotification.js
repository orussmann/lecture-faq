function configureNewLectureNotification(lecturerIds, studentId, role) {
    for (let lecturerId of lecturerIds) {
        console.log(lecturerId);
        const ws = new WebSocket("/app/new-lecture?studentId=" + studentId +"&lecturerId=" + lecturerId + "&role=" + role);
        ws.onmessage = (msg) => {
            const lectureInfo = JSON.parse(msg.data);
            const lecturerFullName = lectureInfo.lecturerSurName + " " + lectureInfo.lecturerLastName;
            const lectureName = lectureInfo.lectureName;
            window.confirm(lecturerFullName + " hat eine neue Vorlesung  erstellt: \"" + lectureName + "\"");
        };
    }
}
// TODO: Implement, removing duplicate code! At this point there are issues with Freemark...
# Lecture FAQ - Live Q&A System for Lectures

## Description

This project is a live Q&A system.

### Problem Scenario

In lectures - especially with high student numbers - it is difficult for lecturers to assess whether all students are still following or if unresolved comprehension questions have arisen. This creates the risk that the pace is too fast and the depth of lecture content is not adequately conveyed. This effect is amplified when students act cautiously to avoid interrupting the lecture or because they are afraid to ask questions publicly. This problem is relevant in both traditional on-site lectures, as well as in online and hybrid formats.

### Solution

The application enables live management of comprehension questions during lectures. Students can ask questions live using their laptop or smartphone, and lecturers can easily conduct comprehension checks for the students.

## Features

### Implemented Features (Pflicht-Features)

- **User Roles**: Two roles implemented - Student (Studierender) and Lecturer (Dozent)
- **Registration & Login**: Users can create accounts via registration form
- **Spring Security Authentication**: Login implemented with Spring Security
- **Live Chat**: Students can join lectures and ask questions publicly (optionally anonymously)
- **User Identification**: Logged-in users display first and last name in chat; anonymous users display a default name
- **Lecture Management**: Only lecturers can create lectures
- **Message Persistence**: Chat messages are stored for each lecture
- **Subscription System**: Students can subscribe to lecturers and receive notifications when new lectures are created
- **Multiple-Choice Polls**: Lecturers can create MC questions; students can answer; results are displayed live with charts
- **Profile Management**: Both students and lecturers can update their profile (email, first name, last name, password)
- **Question Voting**: Students can like questions to indicate relevance
- **Responsive Design**: Modern UI using DaisyUI and Tailwind CSS

### Technology Stack

- **Backend**: Spring Boot (Kotlin)
- **Frontend**: FreeMarker templates with DaisyUI + Tailwind CSS
- **Database**: PostgreSQL
- **Real-time Communication**: WebSockets for live chat and poll updates
- **Authentication**: Spring Security
- **Charting**: Chart.js for poll result visualization

## Installation
 TODO
### Requirements
TODO
### Setup
TODO
#### Local Development
TODO
#### Docker Deployment
TODO

## Usage

### For Lecturers

1. Register as a lecturer
2. Create a new lecture
3. Share the lecture code with students
4. Monitor the live chat during the lecture
5. Create and manage multiple-choice polls for comprehension checks

### For Students

1. Register as a student
2. Subscribe to lecturers to receive notifications
3. Join lectures using the lecture code
4. Ask questions in the live chat
5. Like questions to indicate relevance
6. Participate in multiple-choice polls

## Deployment
TODO

## Course Information

- **Course**: Frameworks, Daten und Dienste im Web (FDDW)
- **Semester**: Summer 2025
- **Institution**: TH Köln

## Roadmap

Potential future enhancements based on bonus features:
- Moderator role with human moderation
- Question highlighting by relevance
- Pinning important questions
- Marking questions as answered
- Email confirmation after registration
- Email confirmation after password update
- Profile picture upload

## Contributing

This is a course project. For contributions or questions, please contact the project maintainer.

## Authors and Acknowledgment

- **Oliver Russmann** - Project Developer
- Developed as part of the FDDW course at TH Köln under the guidance of the course instructors.

## License

This project is developed for educational purposes as part of a university course.

## Project Status

**Active**

### Current Phase
- Core features implemented and deployed
- Student and lecturer views refactored with modern UI (DaisyUI + Tailwind CSS)
- Real-time chat and polling functionality is operational
- Profile management with notifications working


### Next Steps
- Implement AI moderation features
- Add AI tutor assistant for automated question answering
- Enhance the question moderation system


package com.uni.questionview.domain;

public enum AIPrompts {

    RECRUITER_ROLE("You are an Recruiter in a big tech company"),
    CV_ANALYSIS("""
    "In the following message, the text of an applicant's CV will be provided. Based on the applicant's experience and the technologies they are familiar with, please generate three interview questions that a recruiter might ask during an interview. Extract and present the data about the applicant along with the questions in a JSON format, as shown below:
    {
      "firstName": "<Applicant's First Name>",
      "lastName": "<Applicant's Last Name>",
      "previousExperience": [
        {
          "companyName": "<Company Name>",
          "role": "<Role at the Company>",
          "yearsOfExperience": <Years of Experience in the Role>,
          "shortSummary": "<Brief Summary of Responsibilities and Achievements>"
        }
      ],
      "previousEducation": [
        {
          "schoolName": "<Institution Name>",
          "yearsOfEducation": <Total Years of Education>,
          "degree": "<Degree or Certification Obtained>"
        }
      ],
      "technologies": [
        "<List of Technologies the Applicant is Proficient In>"
      ],
      "hobbies": [
        "<Applicant's Hobbies or Interests>"
      ],
      "ideofQuestions": [
        {
          "questionText": "<Interview Question>",
          "correctAnswerText": "<Expected Answer or Key Points>"
        }
      ]
    }
    
    Please ensure the questions are tailored to the applicant's background and are likely to elicit informative responses that would be relevant to assessing their suitability for the position.
    """);
    private final String prompt;

    AIPrompts(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
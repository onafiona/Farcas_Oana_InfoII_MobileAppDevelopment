from django.urls import path
from . import views

urlpatterns = [
    path('materii/', views.materii_list, name='materii_list'),
    # Liste
    path('teme/', views.teme_list, name='teme_list'),
    path('proiecte/', views.proiecte_list, name='proiecte_list'),
    path('examene/', views.examene_list, name='examene_list'),

    # Adăugare
    path('adauga_tema/', views.adauga_tema, name='adauga_tema'),
    path('adauga_proiect/', views.adauga_proiect, name='adauga_proiect'),
    path('adauga_examen/', views.adauga_examen, name='adauga_examen'),

    # Editare
    path('editeaza_tema/<int:pk>/', views.editeaza_tema, name='editeaza_tema'),
    path('editeaza_proiect/<int:pk>/', views.editeaza_proiect, name='editeaza_proiect'),
    path('editeaza_examen/<int:pk>/', views.editeaza_examen, name='editeaza_examen'),

    # Ștergere
    path('sterge_tema/<int:pk>/', views.sterge_tema, name='sterge_tema'),
    path('sterge_proiect/<int:pk>/', views.sterge_proiect, name='sterge_proiect'),
    path('sterge_examen/<int:pk>/', views.sterge_examen, name='sterge_examen'),
]

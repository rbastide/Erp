<script setup>
import { onMounted } from 'vue';

onMounted(() => {
  const savedTheme = localStorage.getItem('user_theme');
  if (savedTheme === 'dark') {
    document.body.classList.add('dark-mode');
  } else {
    document.body.classList.remove('dark-mode');
  }
});
</script>

<template>
  <router-view />
</template>

<style>
body {
  margin: 0;
  padding: 0;
  font-family: 'Roboto', sans-serif;
  background-color: #f8f9fa;
  color: #333;
  transition: background-color 0.3s ease, color 0.3s ease;
}

body.dark-mode {
  background-color: #252525 !important;
  color: #e0e0e0 !important;
}

body.dark-mode .screen-layout,
body.dark-mode .page-container,
body.dark-mode .main-content,
body.dark-mode .main-div,
body.dark-mode .content-wrapper,
body.dark-mode footer {
  background-color: #252525 !important;
  color: #e0e0e0 !important;
}

body.dark-mode .app-header,
body.dark-mode .page-header,
body.dark-mode .sidebar,
body.dark-mode .sidebar-header,
body.dark-mode .sticky-footer,
body.dark-mode .app-footer {
  background-color: #333333 !important;
  border-color: #444444 !important;
  color: #ffffff !important;
  box-shadow: 0 4px 10px rgba(0,0,0,0.3) !important;
}

body.dark-mode .resource-card,
body.dark-mode .user-card,
body.dark-mode .card-action,
body.dark-mode .skill-card,
body.dark-mode .login-card,
body.dark-mode .logout-card,
body.dark-mode .warning-card,
body.dark-mode .success-card,
body.dark-mode .setting-card,
body.dark-mode .help-card,
body.dark-mode .sae-card,
body.dark-mode .hour-card,
body.dark-mode .teacher-card,
body.dark-mode .teacher-display-card,
body.dark-mode .big-square,
body.dark-mode .semester-column,
body.dark-mode .form-card,
body.dark-mode .push-button,
body.dark-mode .version-item,
body.dark-mode .card,
body.dark-mode .summary,
body.dark-mode .error-card,
body.dark-mode .info-footer {
  background-color: #333333 !important;
  border-color: #444444 !important;
  color: #ffffff !important;
  box-shadow: 0 4px 15px rgba(0,0,0,0.3) !important;
}

body.dark-mode .skill-card-resume {
  background-color: rgba(239, 83, 80, 0.15) !important;
  border-color: #ef5350 !important;
}

body.dark-mode .version-item.last-item {
  background-color: #2e2e2e !important;
}

body.dark-mode .add-card {
  background-color: transparent !important;
  border-color: #666 !important;
}
body.dark-mode .add-card:hover {
  background-color: rgba(255, 255, 255, 0.05) !important;
  border-color: #ef5350 !important;
}
body.dark-mode .add-card p {
  color: #ef5350 !important;
}

body.dark-mode h1, body.dark-mode h2, body.dark-mode h3, body.dark-mode h4,
body.dark-mode label,
body.dark-mode .resource-id,
body.dark-mode .sae-number,
body.dark-mode .label,
body.dark-mode .container-connexion p,
body.dark-mode #card-title,
body.dark-mode #title,
body.dark-mode .group-label,
body.dark-mode .field-label,
body.dark-mode .lvl-label,
body.dark-mode .level-title {
  color: #ffffff !important;
}

body.dark-mode .title,
body.dark-mode .section-title,
body.dark-mode .description,
body.dark-mode .title-hour,
body.dark-mode .text-button,
body.dark-mode .version-code,
body.dark-mode .text,
body.dark-mode .highlight,
body.dark-mode .title-centered,
body.dark-mode .section-header,
body.dark-mode .summary-code,
body.dark-mode .footer-value,
body.dark-mode .total-number,
body.dark-mode .ac-tag {
  color: #ef5350 !important;
}

body.dark-mode p,
body.dark-mode .resource-title,
body.dark-mode .sub-text,
body.dark-mode .desc,
body.dark-mode .version-date,
body.dark-mode .resume-item,
body.dark-mode .lvl-desc,
body.dark-mode .sub-text-button,
body.dark-mode .ac-list li,
body.dark-mode .no-result,
body.dark-mode .summary-label,
body.dark-mode .footer-label,
body.dark-mode .separator-text,
body.dark-mode .sub-label {
  color: #bbbbbb !important;
}

body.dark-mode .error-message {
  background-color: rgba(239, 83, 80, 0.2) !important;
  color: #ff8a80 !important;
  border: 1px solid #ef5350 !important;
}

body.dark-mode input[type="text"],
body.dark-mode input[type="password"],
body.dark-mode input[type="number"],
body.dark-mode select,
body.dark-mode textarea,
body.dark-mode .card-input,
body.dark-mode .hour-input,
body.dark-mode .box-input,
body.dark-mode .wide-input,
body.dark-mode .search-input,
body.dark-mode .modern-textarea {
  background-color: #444444 !important;
  border-color: #555555 !important;
  color: #ffffff !important;
}

body.dark-mode input:focus,
body.dark-mode textarea:focus,
body.dark-mode select:focus,
body.dark-mode .search-input:focus {
  border-color: #ef5350 !important;
  box-shadow: 0 0 0 2px rgba(239, 83, 80, 0.2) !important;
}

body.dark-mode input::placeholder,
body.dark-mode textarea::placeholder {
  color: #999 !important;
}

body.dark-mode .card-input.readonly {
  background-color: #2a2a2a !important;
  color: #777 !important;
}

body.dark-mode .grey-square,
body.dark-mode .box-static,
body.dark-mode .read-only-box,
body.dark-mode .lvl-number,
body.dark-mode .input-container {
  background-color: #444444 !important;
  border-color: #555 !important;
  color: #fff !important;
  box-shadow: none !important;
}

body.dark-mode .total-highlight {
  background-color: rgba(239, 83, 80, 0.15) !important;
  color: #ef5350 !important;
  border-color: #ef5350 !important;
}

/* --- TABLES --- */
body.dark-mode .competence-table,
body.dark-mode .competence-table th,
body.dark-mode .competence-table td,
body.dark-mode .lvl-data {
  border-color: #555 !important;
}

body.dark-mode .competence-table th {
  background-color: #2a2a2a !important;
  color: #ef5350 !important;
  border-bottom-color: #444 !important;
}

body.dark-mode .competence-table td {
  border-bottom-color: #444 !important;
  color: #cccccc !important;
}

body.dark-mode .btn-sys,
body.dark-mode .quit-btn,
body.dark-mode .btn-new-user,
body.dark-mode .back-btn,
body.dark-mode .login-button,
body.dark-mode .btn-primary,
body.dark-mode .save-btn,
body.dark-mode .btn-validate {
  background-color: #d32f2f !important;
  color: #fff !important;
  border: none !important;
}

body.dark-mode .btn-sys.primary {
  background: linear-gradient(135deg, #c62828 0%, #ef5350 100%) !important;
  color: #ffffff !important;
}

body.dark-mode .btn-sys.secondary,
body.dark-mode .btn-cancel {
  background-color: #444444 !important;
  color: #ffffff !important;
  border: 1px solid #555555 !important;
}

body.dark-mode .btn-sys:hover,
body.dark-mode .quit-btn:hover,
body.dark-mode .btn-new-user:hover,
body.dark-mode .back-btn:hover,
body.dark-mode .login-button:hover,
body.dark-mode .btn-primary:hover,
body.dark-mode .save-btn:hover {
  background-color: #ef5350 !important;
  transform: translateY(-2px);
}

body.dark-mode .btn-sys.secondary:hover,
body.dark-mode .btn-cancel:hover {
  background-color: #555555 !important;
  color: #ef5350 !important;
}

body.dark-mode .btn-non,
body.dark-mode .btn-oui {
  background-color: #333333 !important;
  color: #ef5350 !important;
  box-shadow: 0 4px 4px rgba(0,0,0,0.5) !important;
}
body.dark-mode .btn-non:hover,
body.dark-mode .btn-oui:hover {
  background-color: #444444 !important;
}

body.dark-mode .step-btn,
body.dark-mode .clear-input-btn {
  background-color: #555 !important;
  color: #fff !important;
}
body.dark-mode .step-btn:hover {
  background-color: #ef5350 !important;
}

body.dark-mode .btn-outline,
body.dark-mode .btn-clear-link,
body.dark-mode .btn-add-line,
body.dark-mode .add-teacher-button,
body.dark-mode .card-ok,
body.dark-mode .close-icon:hover {
  background-color: transparent !important;
  color: #ef5350 !important;
  border-color: #ef5350 !important;
}
body.dark-mode .btn-outline:hover {
  background-color: rgba(239, 83, 80, 0.1) !important;
}

/* --- ICONS & BADGES --- */
body.dark-mode .icon-circle {
  background-color: rgba(255, 255, 255, 0.05) !important;
  color: #ef5350 !important;
}

body.dark-mode .icon-circle.success {
  background-color: rgba(76, 175, 80, 0.15) !important;
  color: #66bb6a !important;
}

body.dark-mode .role-badge {
  background-color: #555 !important;
  color: #fff !important;
}

body.dark-mode .nav-item {
  color: #cccccc !important;
}
body.dark-mode .nav-item:hover,
body.dark-mode .nav-item.active {
  background-color: #444444 !important;
  color: #ef5350 !important;
}
body.dark-mode .nav-item.active {
  border-left-color: #ef5350 !important;
}

body.dark-mode .separator,
body.dark-mode .ue-separator {
  background-image: linear-gradient(to right, rgba(0,0,0,0), rgba(239, 83, 80, 0.5), rgba(0,0,0,0)) !important;
  border-color: rgba(239, 83, 80, 0.3) !important;
}

body.dark-mode .separator-line {
  background-color: #444 !important;
}

body.dark-mode .niveau-group,
body.dark-mode .niveau-container {
  background-color: #2a2a2a !important;
  border-color: #444 !important;
}
body.dark-mode .nested-acs,
body.dark-mode .acs-container {
  background-color: rgba(239, 83, 80, 0.05) !important;
  border-left-color: #ef5350 !important;
}

body.dark-mode input:checked + .slider {
  background-color: #ef5350 !important;
}

body.dark-mode .hamburger-icon,
body.dark-mode .nav-text,
body.dark-mode .sidebar.expanded .hamburger-icon,
body.dark-mode .sidebar.expanded .nav-text{
  color: #ef5350 !important;
}

body.dark-mode .section-main-title {
  color: #ffffff !important;
}

body.dark-mode .hour-badge {
  background-color: #444444 !important;
  color: #ffffff !important;
}

body.dark-mode .sae-chip {
  background-color: rgba(239, 83, 80, 0.15) !important;
  color: #ef5350 !important;
  border-color: #ef5350 !important;
}
body.dark-mode .sae-chip:hover {
  box-shadow: 0 4px 8px rgba(239, 83, 80, 0.2) !important;
}

body.dark-mode .ue-header {
  background: linear-gradient(135deg, #b71c1c 0%, #d32f2f 100%) !important;
  color: #ffffff !important;
}
body.dark-mode .ue-prefix {
  background-color: rgba(0,0,0,0.3) !important;
}

body.dark-mode .circle-number {
  background-color: #ef5350 !important;
  color: #ffffff !important;
}
body.dark-mode .empty-table {
  color: #777 !important;
}

body.dark-mode .total-separator {
  background-color: #444 !important;
}

body.dark-mode .action-icon-btn.add {
  background-color: rgba(46, 125, 50, 0.2) !important;
  color: #81c784 !important;
}
body.dark-mode .action-icon-btn.add:hover {
  background-color: rgba(46, 125, 50, 0.3) !important;
}

body.dark-mode .card-header-view {
  border-bottom-color: #444 !important;
}

body.dark-mode ::-webkit-scrollbar-thumb {
  background: #555 !important;
}

body.dark-mode .edit-scroll-area {
  background-color: #2a2a2a !important;
  border-color: #444 !important;
}

body.dark-mode .edit-level-group {
  background-color: #333 !important;
  border-color: #444 !important;
}

body.dark-mode .card-actions-overlay {
  border-top-color: #444 !important;
}

body.dark-mode .action-btn.edit {
  background-color: rgba(25, 118, 210, 0.2) !important;
  color: #90caf9 !important;
}

body.dark-mode .action-btn.delete {
  background-color: rgba(198, 40, 40, 0.2) !important;
  color: #ef9a9a !important;
}

body.dark-mode .close-icon {
  color: #ef5350 !important;
}

body.dark-mode .btn-dashed {
  border-color: #666 !important;
  color: #bbb !important;
}
body.dark-mode .btn-dashed:hover {
  border-color: #ef5350 !important;
  color: #ef5350 !important;
}

body.dark-mode .add-mini-btn {
  color: #ef5350 !important;
}

body.dark-mode .remove-ac {
  color: #777 !important;
}
body.dark-mode .remove-ac:hover {
  color: #ef5350 !important;
}
</style>
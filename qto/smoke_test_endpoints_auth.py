import requests
import json

BASE_URL = "http://localhost:8080/qto"
TOKEN = "eyJ0eXAiOiJKV1QiLCJub25jZSI6InlSb1ctcXNubnpmajI4al9vQkQ3ZTdiRVB0Y2laSWEwR0ZMbXJYV2NUSnMiLCJhbGciOiJSUzI1NiIsIng1dCI6InNNMV95QXhWOEdWNHlOLUI2ajJ4em1pazVBbyIsImtpZCI6InNNMV95QXhWOEdWNHlOLUI2ajJ4em1pazVBbyJ9.eyJhdWQiOiJodHRwczovL2FkbWluLm1pY3Jvc29mdC5jb20iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC83NTYyMzhkZS00NGZkLTRiMmItOWVmMi1hNWUzNTE4MTczNDYvIiwiaWF0IjoxNzcyNTY2Nzg0LCJuYmYiOjE3NzI1NjY3ODQsImV4cCI6MTc3MjU3MDc2OCwiYWNyIjoiMSIsImFjcnMiOlsicDEiLCJ1cm46dXNlcjpyZWdpc3RlcnNlY3VyaXR5aW5mbyJdLCJhaW8iOiJBWFFBaS84YkFBQUF6WnhUV0J2WS9ham9GemxXUG5QN2s5WVIyM2xkb2tHcmI0V01CYVg4VTJSajRDWEFQNkdIR3VtWmIvSVIyUldaazZrVTBuTnZ4UzBwU0lmRTArQWIrNjRNbWxIamU5alQ0QVE4VndzelUrcnlWcml4b2ZFNi81UXZMNFZ4dFM3V0duTnM2dG0vZjYzUEMvWFF6TFRUYVE9PSIsImFtciI6WyJwd2QiLCJyc2EiLCJtZmEiXSwiYXBwaWQiOiJhMjc2MGM0MS02M2M5LTQyYjUtOGQ1OC1iZmExZmQ5ZTJlYjMiLCJhcHBpZGFjciI6IjAiLCJkZXZpY2VpZCI6IjczYjFkZDE5LTVhNzctNDkxNS1iNGZiLWZiYjk5ZGI1NTYxNiIsImdpdmVuX25hbWUiOiJOaWdlbCIsImlkdHlwIjoidXNlciIsImlwYWRkciI6IjI3LjQ5LjEwLjI1NSIsIm5hbWUiOiJOaWdlbCBBcnVnYXkiLCJvaWQiOiI0Y2I1MWJkYi05ZjcyLTRlOTUtYTA3Zi1mYjRkZjJjYWNiMDAiLCJvbnByZW1fc2lkIjoiUy0xLTUtMjEtMzAzNjE2MjM2NC0xOTk0ODkxMjQyLTE2NjgxNTU3MzItMTUzMSIsInB1aWQiOiIxMDAzMjAwNTdERDhENkYxIiwicmgiOiIxLkFYZ0EzamhpZGYxRUswdWU4cVhqVVlGelJnWUFBQUFBQVBFUHpnQUFBQUFBQUFCNEFMbDRBQS4iLCJzY3AiOiJNMzY1QWRtaW5Qb3J0YWwuU2VsZlNlcnZpY2VSZXF1ZXN0LlJlYWRXcml0ZSIsInNpZCI6IjAwMTIzMDVhLWUyNGQtNmUzMy04Y2M2LWM2MTYzYWVkOTYyNSIsInNpZ25pbl9zdGF0ZSI6WyJkdmNfbW5nZCIsImR2Y19jbXAiLCJrbXNpIl0sInN1YiI6InNFVTlwcUdDQU13UFVlcjRfcGF0Z1RWVTZfWnY0eXI3QVUtOEpCYXNEcjQiLCJ0ZW5hbnRfcmVnaW9uX3Njb3BlIjoiTkEiLCJ0aWQiOiI3NTYyMzhkZS00NGZkLTRiMmItOWVmMi1hNWUzNTE4MTczNDYiLCJ1bmlxdWVfbmFtZSI6Im5pZ2VsLmFydWdheUBlbmRlYXZvci1tcy5jb20iLCJ1cG4iOiJuaWdlbC5hcnVnYXlAZW5kZWF2b3ItbXMuY29tIiwidXRpIjoiSHNxQ0tWNzZ1RTZUSk1HWklBcWtBQSIsInZlciI6IjEuMCIsIndpZHMiOlsiYjc5ZmJmNGQtM2VmOS00Njg5LTgxNDMtNzZiMTk0ZTg1NTA5Il0sInhtc19hY3RfZmN0IjoiMyA1IiwieG1zX2Z0ZCI6InBQaU10WlBjSGtucF9lZnJid1BwZ3p2N0tfYnJOOHd4NEl1bGpmdnVuRWtCZFhOM1pYTjBNeTFrYzIxeiIsInhtc19pZHJlbCI6IjI2IDEiLCJ4bXNfc3ViX2ZjdCI6IjMgNiJ9.UR6pCqqSC41l5wCV-9z_cTOs0vJiQByMxSoXUxWLCiCchq7SD6r2-P9gD28D_qT4RdVfh3KPAipG_Fu0z8j4JrHPqK3A7C2M1ZO2UprVTObkft1P4PX-xqXg1H3SMQzyfIThc9KtYS6JdTMLOVhWdstYn9k7EO34qi-GTwZ65DW7kLCEGxjADdJN4o984IfilQyHMhnU-XIbWlX14GZrKSZqI4933iXfz2Kx7jtl7UnMXYLwz8j4eS2fCOBV7Npq1nm0QbBQJ7_Pl-q93Ime0wzDo59WxuDI8i0mcO27NmvmdgG7VQNr0G5iksGdZ5SFZOhx--YLBiWULw1G1c7lOQ"

def print_result(name, resp):
    print(f"{name}: {resp.status_code}")
    try:
        print(json.dumps(resp.json(), indent=2))
    except Exception:
        print(resp.text)
    print("-"*40)

endpoints = [
    ("Get Activation Schedules", "GET", f"{BASE_URL}/api/activationSchedules?serviceId=1", None),
    ("Get Activation Schedule by ID", "GET", f"{BASE_URL}/api/activationSchedules/1", None),
    ("Create Activation Schedule", "POST", f"{BASE_URL}/api/activationSchedules", {"serviceId": 1, "name": "Test Schedule"}),
    ("Edit Activation Schedule", "PUT", f"{BASE_URL}/api/activationSchedules/1", {"id": 1, "serviceId": 1, "name": "Updated Schedule"}),
    ("Get Addresses", "GET", f"{BASE_URL}/api/addresses", None),
    ("Get Company File Attachments", "GET", f"{BASE_URL}/api/companyFileAttachments?companyId=1", None),
    ("Upload Company File Attachment", "POST", f"{BASE_URL}/api/companyFileAttachments/upload?companyId=1", None),
    ("Get Cost History", "GET", f"{BASE_URL}/api/costHistory", None),
    ("Get Cost History Meta", "GET", f"{BASE_URL}/api/costHistory/meta", None),
    ("Find Contact by CompanyId and Type", "GET", f"{BASE_URL}/api/contacts?companyId=1&type=SALES", None),
    ("Find Contact by OrderId", "GET", f"{BASE_URL}/api/contacts/order?orderId=1", None),
    ("Create Contact", "POST", f"{BASE_URL}/api/contacts", {"companyId": 1, "type": "SALES", "name": "Test Contact"}),
    ("Edit Contact", "PUT", f"{BASE_URL}/api/contacts/1", {"id": 1, "companyId": 1, "type": "SALES", "name": "Updated Contact"}),
    ("Get Location Jeops", "GET", f"{BASE_URL}/api/locationJeops", None),
    ("Create Location Jeop", "POST", f"{BASE_URL}/api/locationJeops", {"locationId": 1, "name": "Test Jeop"}),
    ("Edit Location Jeop", "PUT", f"{BASE_URL}/api/locationJeops/1", {"id": 1, "locationId": 1, "name": "Updated Jeop"}),
    ("Get Locations", "GET", f"{BASE_URL}/api/locations", None),
    ("Create Location", "POST", f"{BASE_URL}/api/locations", {"name": "Test Location"}),
    ("Edit Location", "PUT", f"{BASE_URL}/api/locations/1", {"id": 1, "name": "Updated Location"}),
    ("Delete Location", "DELETE", f"{BASE_URL}/api/locations/1", None),
    ("List Lookup Types", "GET", f"{BASE_URL}/api/lookupTypes?offset=0&limit=10", None),
    ("Set Lookup Type Values", "PUT", f"{BASE_URL}/api/lookupTypes/1", {"id": 1, "name": "Updated LookupType"}),
    ("Get Order Jeops", "GET", f"{BASE_URL}/api/orderJeops", None)
]

for name, method, url, data in endpoints:
    try:
        headers = {"Authorization": f"Bearer {TOKEN}"}
        if method == "GET" or method == "DELETE":
            resp = requests.request(method, url, headers=headers)
        else:
            resp = requests.request(method, url, json=data, headers=headers)
        print_result(name, resp)
    except Exception as e:
        print(f"{name}: ERROR {e}")
        print("-"*40)
